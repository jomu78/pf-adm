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

## Template Compatibility Rules

- Treat `pf-adm-template` as structurally compatible with AdminFaces where practical, but do not assume full feature parity.
- Preserve the `admin.xhtml` and `admin-base.xhtml` Facelets contract and keep compatibility-oriented `ui:define` / `ui:insert` regions stable unless there is a strong reason to change them.
- Keep the top-left branding block in the sidebar/menu area. Do not move it into the header/navbar again.
- Do not introduce control-sidebar behavior unless explicitly requested. Placeholder compatibility hooks may exist, but they must not be documented or treated as full feature support.
- Prefer modern internal bean names such as `layoutView`, `skinView`, and `pfAdmConfig` over legacy `*MB` naming. Compatibility aliases should only be added when explicitly needed for migration support.
- When editing template compatibility, distinguish clearly between:
  - structural compatibility of Facelets regions
  - behavioral compatibility of implemented features

## Skin Handling Rules

- `pf-adm.skin` is the default skin for newly created sessions.
- Skin changes made through `SkinView` must remain session-local and must not affect other users.
- Validate skin values against `SkinEnum` and keep `SkinEnum` aligned with the available theme skins.

## Documentation Rules

- Use AsciiDoc-native link syntax such as `link:doc/file.adoc[Label]`, not Markdown links, in `.adoc` files.
- Keep cross-document filenames and links in sync when renaming docs. The canonical configuration matrix filename is `doc/configuration_compatibility_matrix.adoc`.
- Keep the compatibility docs consistent with actual implementation state:
  - `doc/template_compatibility_matrix.adoc` is about Facelets/template hook compatibility
  - `doc/feature_matrix.adoc` is about implemented behavior
  - `doc/uicomponents.adoc` is about component-family styling/support
  - `doc/configuration_compatibility_matrix.adoc` is about configuration property availability
- Do not mark a feature as supported in documentation when only a placeholder hook exists.
- For migration guidance, prefer modern `pf-adm` naming and conventions over preserving legacy AdminFaces naming.

## Verification

- After SCSS changes, run `mvn -pl pf-adm-theme -DskipTests generate-resources`.
- After theme/template integration changes, run `mvn -pl pf-adm-theme,pf-adm-template -DskipTests compile`.
- Documentation-only changes do not require a Maven build unless they also modify examples, generated assets, or code snippets tied to build output.
