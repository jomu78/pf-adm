const SIDEBAR_SELECTOR = '.sidebar-menu, .nav-sidebar';
const SIDEBAR_LINK_SELECTOR = '.nav-link';
const ACTIVE_CLASS = 'active';
const MENU_OPEN_CLASS = 'menu-open';
const ACTIVE_LINK_MARKER = 'pfadm-nav-link-active';
const OPEN_ITEM_MARKER = 'pfadm-nav-path-open';
const PARENT_LINK_MARKER = 'pfadm-nav-path-parent';

$(document).ready(function() {
  setActiveNavLink();
  bindAjaxMenuRefresh();
});

function bindAjaxMenuRefresh() {
  if (!window.jsf || !window.jsf.ajax || typeof window.jsf.ajax.addOnEvent !== 'function') {
    return;
  }

  window.jsf.ajax.addOnEvent(function(event) {
    if (event && event.status === 'success') {
      setActiveNavLink();
    }
  });
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
