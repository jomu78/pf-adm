// '.pfadm-top-menu' is the primary navigation list in the top-menu layout
// (admin-top.xhtml); the sidebar selectors cover the default left-menu layout.
const SIDEBAR_SELECTOR = '.sidebar-menu, .nav-sidebar, .pfadm-top-menu';
const SIDEBAR_LINK_SELECTOR = '.nav-link';
const ACTIVE_CLASS = 'active';
const MENU_OPEN_CLASS = 'menu-open';
const ACTIVE_LINK_MARKER = 'pfadm-nav-link-active';
const OPEN_ITEM_MARKER = 'pfadm-nav-path-open';
const PARENT_LINK_MARKER = 'pfadm-nav-path-parent';

// Menu search: '.pfadm-menu-search' is the search input rendered into the
// sidebar when pf-adm.render-menu-search is enabled.
const MENU_SEARCH_SELECTOR = '.pfadm-menu-search';
const MENU_SEARCH_ACTIVE_CLASS = 'pfadm-menu-search-active';
// Marks nav-items expanded by the search so they can be collapsed again on
// clear without disturbing the active-path expansion (OPEN_ITEM_MARKER).
const SEARCH_OPEN_MARKER = 'pfadm-menu-search-open';

// Orphaned overlay panels: PrimeFaces suffixes an overlay panel's id with this
// and hoists the panel to <body> when its component sits in a dialog (see
// dropOrphanOverlayPanels).
const OVERLAY_PANEL_ID_SUFFIX = '_panel';

// Scroll-to-top: '.pfadm-scroll-top' is the floating button rendered when
// pf-adm.render-scroll-to-top is enabled.
const SCROLL_TOP_SELECTOR = '.pfadm-scroll-top';
const SCROLL_TOP_VISIBLE_CLASS = 'pfadm-scroll-top-visible';
const SCROLL_TOP_THRESHOLD = 200;

// Form asterisks: when pf-adm.render-form-asterisks is enabled the body carries
// this class, and a '*' indicator is appended to the label of every required
// input (see applyFormAsterisks).
const FORM_ASTERISKS_BODY_CLASS = 'pfadm-form-asterisks';
const REQUIRED_INDICATOR_CLASS = 'pfadm-required-indicator';
// Required form controls expose aria-required="true" (PrimeFaces) or the native
// required attribute. A label is matched to its control via for=<control id>.
const REQUIRED_CONTROL_SELECTOR = '[aria-required="true"], [required]';
// PrimeFaces' own outputLabel required indicator -1 skip labels that already
// carry one so we never render a double asterisk.
const EXISTING_INDICATOR_SELECTOR = '.ui-outputlabel-rfi, .' + REQUIRED_INDICATOR_CLASS;

// Lift PrimeFaces overlays above the AdminLTE chrome. PrimeFaces assigns overlay
// z-indexes dynamically from PrimeFaces.zindex (default base 1000), but the
// AdminLTE 4 sidebar sits at 1038 and header at 1034, so a default dialog would
// render behind them. Raising the base clears the chrome while staying just below
// Bootstrap's modal layer (1055), and preserves PrimeFaces' per-overlay increments
// (stacked dialogs, overlaypanel-over-dialog keep their relative order).
const PFADM_ZINDEX_BASE = 1100;

// Applied more than once on purpose. A single assignment at script-execution time
// is lost whenever PrimeFaces' own core script is evaluated after this one - it
// declares the 1000 base itself, so it resets us. That ordering is not ours to
// control: it happens with primefaces.MOVE_SCRIPTS_TO_BOTTOM and with hand-written
// templates that pull the resources in a different order. Re-asserting on ready and
// after every AJAX request is cheap and makes the base stick regardless.
// Only ever raises: PrimeFaces increments the value per shown overlay, and lowering
// it back to the base would drop stacked overlays behind the ones below them.
function raisePrimeFacesZindex() {
    if (window.PrimeFaces && PrimeFaces.zindex < PFADM_ZINDEX_BASE) {
        PrimeFaces.zindex = PFADM_ZINDEX_BASE;
    }
}

raisePrimeFacesZindex();

$(document).ready(function() {
    raisePrimeFacesZindex();
  setActiveNavLink();
  bindAjaxMenuRefresh();
  bindMenuSearch();
  bindScrollTop();
  applyFormAsterisks();
});

function bindAjaxMenuRefresh() {
  // PrimeFaces AJAX (p:ajax, p:commandButton, ...) does not use the standard
  // Faces AJAX API. It dispatches this jQuery event on document instead.
  $(document).on('pfAjaxComplete', function() {
      raisePrimeFacesZindex();
    setActiveNavLink();
    applyFormAsterisks();
  });

  // Standard Jakarta Faces AJAX (f:ajax). Faces 4 exposes the 'faces' namespace;
  // the legacy 'jsf' namespace was removed and is no longer available.
  if (window.faces && window.faces.ajax && typeof window.faces.ajax.addOnEvent === 'function') {
    window.faces.ajax.addOnEvent(function(event) {
      if (event && event.status === 'success') {
        setActiveNavLink();
        applyFormAsterisks();
      }
    });
  }
}

function setActiveNavLink() {
  const $sidebars = $(SIDEBAR_SELECTOR);

  if ($sidebars.length === 0) {
    return;
  }

  clearSidebarState($sidebars);

  const currentPath = normalizePath(window.location.href);
  let bestMatch = null;
  let bestScore = -1;

  $sidebars.find(SIDEBAR_LINK_SELECTOR).each(function() {
    const $link = $(this);
    const rawHref = $link.attr('href');

    if (!isSelectableNavLink($link, rawHref)) {
      return;
    }

    const linkPath = normalizePath(rawHref);
    const matchScore = getMatchScore(currentPath, linkPath);

    if (matchScore > bestScore) {
      bestMatch = $link;
      bestScore = matchScore;
    }
  });

  if (!bestMatch || bestScore < 0) {
    return;
  }

  activateNavLink(bestMatch);
}

function clearSidebarState($sidebars) {
  $sidebars.find('.' + ACTIVE_LINK_MARKER)
    .removeClass(ACTIVE_CLASS + ' ' + ACTIVE_LINK_MARKER);

  $sidebars.find('.' + PARENT_LINK_MARKER)
    .removeClass(PARENT_LINK_MARKER)
    .attr('aria-expanded', 'false');

  $sidebars.find('.' + OPEN_ITEM_MARKER)
    .removeClass(MENU_OPEN_CLASS + ' ' + OPEN_ITEM_MARKER);
}

function isSelectableNavLink($link, rawHref) {
  if (!rawHref || rawHref === '#') {
    return false;
  }

  if ($link.data('lteToggle') === 'treeview') {
    return false;
  }

  return !rawHref.startsWith('javascript:');
}

function normalizePath(rawUrl) {
  if (!rawUrl) {
    return null;
  }

  let url;

  try {
    url = new URL(rawUrl, window.location.href);
  } catch (error) {
    return null;
  }

  if (url.origin !== window.location.origin) {
    return null;
  }

  let path = url.pathname || '/';

  path = path.replace(/(^\/[^/]+)?\/faces(?=\/|$)/, function(match, contextPath) {
    return contextPath || '';
  });

  if (path.length > 1) {
    path = path.replace(/\/+$/, '');
  }

  return path || '/';
}

function getMatchScore(currentPath, linkPath) {
  if (!currentPath || !linkPath) {
    return -1;
  }

  if (currentPath === linkPath) {
    return linkPath.length + 1000;
  }

  if (linkPath !== '/' && currentPath.endsWith(linkPath)) {
    return linkPath.length;
  }

  return -1;
}

function activateNavLink($link) {
  $link.addClass(ACTIVE_CLASS + ' ' + ACTIVE_LINK_MARKER);

  const $navItem = $link.closest('.nav-item');
  const $childTreeview = $navItem.children('.nav-treeview');

  if ($childTreeview.length > 0) {
    $navItem.addClass(MENU_OPEN_CLASS + ' ' + OPEN_ITEM_MARKER);
    $link.addClass(PARENT_LINK_MARKER).attr('aria-expanded', 'true');
  }

  $link.parents('.nav-treeview').each(function() {
    const $treeview = $(this);
    const $parentItem = $treeview.closest('.nav-item');
    const $parentLink = $parentItem.children('.nav-link').first();

    $parentItem.addClass(MENU_OPEN_CLASS + ' ' + OPEN_ITEM_MARKER);
    $parentLink.addClass(PARENT_LINK_MARKER).attr('aria-expanded', 'true');
  });
}

function bindMenuSearch() {
  $(MENU_SEARCH_SELECTOR).each(function() {
    const $input = $(this);
    const $menu = $input.closest('.app-sidebar').find(SIDEBAR_SELECTOR).first();

    if ($menu.length === 0) {
      return;
    }

    $input.on('input', function() {
      filterMenu($menu, ($input.val() || '').trim().toLowerCase());
    });
  });
}

function filterMenu($menu, query) {
  // Undo any expansion a previous query introduced, but keep the active path
  // (OPEN_ITEM_MARKER) expanded.
  $menu.find('.' + SEARCH_OPEN_MARKER).each(function() {
    const $item = $(this);
    $item.removeClass(SEARCH_OPEN_MARKER);
    if (!$item.hasClass(OPEN_ITEM_MARKER)) {
      $item.removeClass(MENU_OPEN_CLASS);
    }
  });

  const $items = $menu.find('.nav-item');

  if (!query) {
    $items.css('display', '');
    $menu.removeClass(MENU_SEARCH_ACTIVE_CLASS);
    return;
  }

  $menu.addClass(MENU_SEARCH_ACTIVE_CLASS);
  $items.css('display', 'none');

  $menu.find(SIDEBAR_LINK_SELECTOR).each(function() {
    const $link = $(this);
    // A treeview parent keeps its children in a sibling .nav-treeview, so the
    // link's own text is just its label and never includes child labels.
    const text = ($link.text() || '').trim().toLowerCase();

    if (!text || text.indexOf(query) === -1) {
      return;
    }

    const $item = $link.closest('.nav-item');
    // Reveal the match, all of its descendants (a matched parent shows its
    // whole subtree), and all of its ancestors (expanding them on the way up).
    $item.css('display', '');
    $item.find('.nav-item').css('display', '');
    $item.parents('.nav-item').each(function() {
      const $ancestor = $(this);
      $ancestor.css('display', '');
      if (!$ancestor.hasClass(MENU_OPEN_CLASS)) {
        $ancestor.addClass(MENU_OPEN_CLASS + ' ' + SEARCH_OPEN_MARKER);
      }
    });
  });
}

function bindScrollTop() {
  const $btn = $(SCROLL_TOP_SELECTOR);

  if ($btn.length === 0) {
    return;
  }

  // In the AdminLTE fixed layout the page content scrolls with the window.
  const currentScroll = function() {
    return window.pageYOffset || document.documentElement.scrollTop || 0;
  };

  const toggle = function() {
    $btn.toggleClass(SCROLL_TOP_VISIBLE_CLASS, currentScroll() > SCROLL_TOP_THRESHOLD);
  };

  $(window).on('scroll', toggle);
  toggle();

  $btn.on('click', function(event) {
    event.preventDefault();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  });
}

function applyFormAsterisks() {
  // Self-guarding: only runs when pf-adm.render-form-asterisks set the body
  // class, so it is safe to call unconditionally (e.g. from AJAX handlers).
  if (!document.body || !document.body.classList.contains(FORM_ASTERISKS_BODY_CLASS)) {
    return;
  }

  $(REQUIRED_CONTROL_SELECTOR).each(function() {
    const id = this.id;
    if (!id) {
      return;
    }

    // A control is "labelled" when an outputLabel/label targets it via for=id.
    const $label = $('label[for="' + $.escapeSelector(id) + '"]');
    if ($label.length === 0 || $label.find(EXISTING_INDICATOR_SELECTOR).length > 0) {
      return;
    }

    $label.append(
      $('<span>', { 'class': REQUIRED_INDICATOR_CLASS, 'aria-hidden': 'true', text: '*' })
    );
  });
}

// ---------------------------------------------------------------------------
// Orphaned overlay panels
// ---------------------------------------------------------------------------

// Removes overlay panels that PrimeFaces hoisted to <body> and that are about to
// be re-rendered, identified by the client ids in `scope`.
//
// Opt-in on purpose: this is a rare situation, and it deletes DOM nodes. Call it
// from the onstart of the request that re-renders the region - typically a button
// that both refreshes a dialog's form and opens the dialog:
//
//   <p:commandButton action="#{view.edit}"
//                    onstart="PfAdm.dropOrphanOverlayPanels('editDialogForm')"
//                    update=":editDialogForm"
//                    oncomplete="PF('editDialogVar').show()"/>
//
// Why it is needed: when a selectOneMenu, autoComplete, or selectCheckboxMenu sits
// inside a dialog, PrimeFaces.utils.resolveAppendTo forces appendTo="@(body)" on it
// and moves its panel out to <body>. The panel then no longer belongs to the region
// it was declared in, so an AJAX update of that region re-renders a second panel
// carrying the same DOM id while the hoisted one survives. From there the component
// cannot recover: PrimeFaces resolves its panel as $('#form\:field_panel'), the
// escaped colon makes jQuery fall back to querySelectorAll, and the widget ends up
// holding BOTH elements. alignPanel() then sees one of the two parents match the
// component, applies coordinates relative to it, and the browser resolves them
// against the viewport because the panel is position: fixed - the dropdown opens in
// the top left corner of the screen. appendDynamicOverlay bails out too, because one
// panel already sits in <body>, so PrimeFaces never cleans up.
//
// Dropping the hoisted panel before the response arrives leaves exactly one element
// per id, so the widget resolves it unambiguously and PrimeFaces moves it as
// intended.
//
// Pass the client ids of the re-rendered region, as a space- or comma-separated
// string or an array - the same ids the request's update targets. Only panels whose
// owning component is inside that region are touched; removing others would detach
// the panels of widgets that are not being re-rendered. Returns the number removed.
//
// Note that the panels are gone once the request is sent: if it fails or is aborted,
// the old markup stays without its panels until the next successful update.
function dropOrphanOverlayPanels(scope) {
    const ids = Array.isArray(scope) ? scope : String(scope || '').split(/[\s,]+/);
    const targets = ids.filter(Boolean);
    if (targets.length === 0) {
        return 0;
    }

    let removed = 0;

    // Only direct children of <body>: that is where PrimeFaces hoists panels to. A
    // panel still nested in its component is the freshly rendered one and must stay.
    Array.prototype.slice.call(document.body.children).forEach(function (element) {
        const id = element.id;
        if (!id || !id.endsWith(OVERLAY_PANEL_ID_SUFFIX)) {
            return;
        }

        const ownerId = id.slice(0, -OVERLAY_PANEL_ID_SUFFIX.length);
        const inScope = targets.some(function (target) {
            // The owner is re-rendered when it is the target itself, or a component
            // below it - client ids of nested components carry the target as a prefix.
            return ownerId === target || ownerId.startsWith(target + ':');
        });

        if (inScope) {
            element.remove();
            removed++;
        }
    });

    return removed;
}

// Public API. Everything else in this file is internal to the template.
window.PfAdm = window.PfAdm || {};
window.PfAdm.dropOrphanOverlayPanels = dropOrphanOverlayPanels;
