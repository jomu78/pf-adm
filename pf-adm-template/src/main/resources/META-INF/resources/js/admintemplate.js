$(document).ready(function() {

  // Run on page load
  setActiveNavLink();
});

// Function to set active nav-link
function setActiveNavLink() {
  let currentPath = window.location.pathname;
  // Remove query parameters and anchors
  let currentPathClean = currentPath.split('?')[0].split('#')[0];

  $('.nav-sidebar .nav-link').each(function() {
    let linkPath = $(this).attr('href');
    if (!linkPath) return;

    // Remove query parameters and anchors
    let linkPathClean = linkPath.split('?')[0].split('#')[0];

    // Check if current path ends with the link path
    if (currentPathClean.endsWith(linkPathClean)) {
      // Remove active class from all links
      $('.nav-sidebar .nav-link').removeClass('active');
      // Add active class to the matching link
      $(this).addClass('active');
      // Add active class to parent nav-item
      $(this).closest('.nav-item').addClass('active');
      // Open parent treeview menu if exists
      $(this).closest('.has-treeview').addClass('menu-open');
    }
  });
}