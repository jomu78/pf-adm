# AGENTS.md

## Theme Customization Rules

- Do not edit files under `pf-adm-theme/src/main/resources/static`. These are vendored Bootstrap/AdminLTE sources.
- Put all theme customizations in `pf-adm-theme/src/main/scss/pfadm` and wire them through `pf-adm-theme/src/main/scss/theme.scss`.
- Treat `pf-adm-theme/src/main/scss/pfadm/_variables.scss` as the source of truth for design tokens.
- Prefer token-based styling (`pfadm-*`) over hardcoded colors, borders, shadows, and spacing.
- Preserve AdminFaces migration compatibility for semantic color names and classes such as `default`, `primary`, `success`, `info`, `warning`, `danger`, `fatal`, `teal`, `btn-*`, and `box-*`.
- Preserve AdminFaces-compatible skin names. Keep `SkinEnum` and `pfadm/skins` in sync.
- Implement dark/light mode through the local `pfadm` layer and CSS variables, not by patching vendor sources.
- Prefer central fixes in shared mixins, tokens, or compatibility layers over one-off per-component and per-skin patches.

## Verification

- After SCSS changes, run `mvn -pl pf-adm-theme -DskipTests generate-resources`.
- After theme/template integration changes, run `mvn -pl pf-adm-theme,pf-adm-template -DskipTests compile`.
