package arrow.framework.faces.listener;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.application.DialogActionListener;
import org.primefaces.component.resetinput.ResetInputActionListener;

public class ArrowActionListener implements ActionListener {

  private ActionListener base;
  private DialogActionListener dialogListener;
  private ResetInputActionListener resetInputListener;

  public ArrowActionListener(ActionListener base) {
    this.base = base;
    this.dialogListener = new DialogActionListener(base);
    this.resetInputListener = new ResetInputActionListener();
  }

  @Override
  public void processAction(ActionEvent event) throws AbortProcessingException {
    this.dialogListener.processAction(event);
    this.resetInputListener.processAction(event);
    this.base.processAction(event);
  }

}
