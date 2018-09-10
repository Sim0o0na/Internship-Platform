$(function() {
    loadPartialPanels();
});
function loadPartialPanels() {
    var panel = getParameterByName('showpanel', window.location.href);
    if (panel === "user-applications") {
        loadUserApplicationsPanel()
    }
}