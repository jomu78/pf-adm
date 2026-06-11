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

$(document).ready(function() {
  setActiveNavLink();
  bindAjaxMenuRefresh();
  bindMenuSearch();
});

function bindAjaxMenuRefresh() {
  // PrimeFaces AJAX (p:ajax, p:commandButton, ...) does not use the standard
  // Faces AJAX API. It dispatches this jQuery event on document instead.
  $(document).on('pfAjaxComplete', function() {
    setActiveNavLink();
  });

  // Standard Jakarta Faces AJAX (f:ajax). Faces 4 exposes the 'faces' namespace;
  // the legacy 'jsf' namespace was removed and is no longer available.
  if (window.faces && window.faces.ajax && typeof window.faces.ajax.addOnEvent === 'function') {
    window.faces.ajax.addOnEvent(function(event) {
      if (event && event.status === 'success') {
        setActiveNavLink();
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
