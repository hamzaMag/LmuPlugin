package diallomagroun.lmu.ui;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class GenerateFromFileMenu extends AbstractHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub	
	}

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		
		String[] choices = { "PDF", "LMU", "TXT", "JAVA", "JAR", "F" };
	    String extension = (String) JOptionPane.showInputDialog(null, "Choose now...","The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,choices,choices[1]);
		
	    IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();
		if (selection == null) {
			return null;
		}
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        String pathToWorkspace = workspace.getRoot().getLocation().toFile().toString();
		
		Object firstElement = selection.getFirstElement();
		if(firstElement.getClass().getName() == "org.eclipse.jdt.internal.core.CompilationUnit") {
			ICompilationUnit javaFile = (ICompilationUnit) firstElement;
			try {
				JOptionPane.showMessageDialog(null,javaFile.getElementName() + "\n" + pathToWorkspace + javaFile.getPath());
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		} else if(firstElement.getClass().getName() == "org.eclipse.core.internal.resources.File") {
			IFile jarFile = (IFile) firstElement;
			try {
				JOptionPane.showMessageDialog(null,jarFile.getName() + "\n" + jarFile.getProject());
			} catch(HeadlessException e) {
				e.printStackTrace();
			}
		} else if(firstElement.getClass().getName() == "org.eclipse.jdt.internal.core.PackageFragment") {
			IPackageFragment pack = (IPackageFragment) firstElement;
			try {
				JOptionPane.showMessageDialog(null,pack.getElementName() + "\n" + pack.getCompilationUnits().length);
			} catch(HeadlessException e) {
				e.printStackTrace();
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(firstElement.getClass().getName() == "org.eclipse.jdt.core.IJavaProject") {
			IJavaProject javaProject = (IJavaProject) firstElement;
			try {
				JOptionPane.showMessageDialog(null,javaProject.getElementName());
			} catch(HeadlessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}