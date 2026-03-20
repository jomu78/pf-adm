package de.muehlencord.pfadm.showcase.view;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * backing bean for tree showcase page
 *
 * @author Joern Muehlencord, 2026-03-20
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
@Setter
public class TreeView implements Serializable {

  private TreeNode<String> root;
  private TreeNode<String> selectedNode;
  private TreeNode<String>[] selectedNodes;

  @PostConstruct
  public void init() {
    root = new DefaultTreeNode<>("Root", null);

    TreeNode<String> applications = new DefaultTreeNode<>("Applications", root);
    TreeNode<String> showcase = new DefaultTreeNode<>("Showcase", applications);
    new DefaultTreeNode<>("Datatable module", showcase);
    new DefaultTreeNode<>("Theme previews", showcase);
    new DefaultTreeNode<>("Component demos", showcase);

    TreeNode<String> template = new DefaultTreeNode<>("Template", applications);
    new DefaultTreeNode<>("Sidebar", template);
    new DefaultTreeNode<>("Top navigation", template);
    new DefaultTreeNode<>("Skin handling", template);

    TreeNode<String> documentation = new DefaultTreeNode<>("Documentation", root);
    new DefaultTreeNode<>("Feature matrix", documentation);
    new DefaultTreeNode<>("Template compatibility", documentation);
    new DefaultTreeNode<>("UI components", documentation);

    TreeNode<String> backlog = new DefaultTreeNode<>("Backlog", root);
    new DefaultTreeNode<>("FileUpload styling", backlog);
    new DefaultTreeNode<>("Steps styling", backlog);
    new DefaultTreeNode<>("Wizard styling", backlog);

    root.setExpanded(true);
    applications.setExpanded(true);
    showcase.setExpanded(true);
  }
}
