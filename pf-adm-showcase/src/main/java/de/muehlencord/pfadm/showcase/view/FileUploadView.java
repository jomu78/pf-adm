package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 * backing bean for file upload showcase page
 *
 * @author Joern Muehlencord, 2026-03-20
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
public class FileUploadView implements Serializable {

  private final List<String> uploadedFiles = new ArrayList<>();

  public void onUpload(FileUploadEvent event) {
    UploadedFile file = event.getFile();
    uploadedFiles.add(file.getFileName());

    var message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Uploaded", file.getFileName());
    FacesContext.getCurrentInstance().addMessage(null, message);
  }
}
