package diallomagroun.lmu.ui;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jdt.launching.JavaRuntime;

//https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Class_Loading_in_a_running_plugin#Retrieve_the_class_loader_of_a_Java_project
//https://github.com/PETILLON-Sebastien/lmu-eclipse-plugin/blob/master/lmu-eclipse-plugin-bkp/src/lmueclipsepluginbkp/handlers/GenerateHandler.java
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
		HandlePath(event);
		/*try {
			getClassPathFromProject(event);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return null;
	}
	
	public void chooseFolder(ExecutionEvent event) {
		String filename = File.separator;
		//Get our file saver to the screen
		
		
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();
		Object firstElement = selection.getFirstElement();
		//ICompilationUnit javaFile = (ICompilationUnit) firstElement;
		
	    JFileChooser fc = new JFileChooser(new File(filename));
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Only able to select directiories
	    // Show open dialog; this method does not return until the dialog is closed
	    fc.showSaveDialog(null);
	    File selectedLocation = fc.getSelectedFile();//fc.getCurrentDirectory(); //Gets the selected Location
	    //Sets the path of the file so we can read from it.
	    String filePath = selectedLocation.getAbsolutePath();
	    //String name = JOptionPane.showInputDialog("What name do you want to give the file?");
	    //Temporary code bellow will change to StringBuilder here.
	    //filePath = filePath + "/" + name + ".txt";
	    System.out.println("selectedLocation : " + selectedLocation.getName() + "\n" + fc.getCurrentDirectory());
	    System.out.println("Filename " + filename + "\n" + "Filepath " + filePath);
	}
	
	
	/*public void HandlePath(ExecutionEvent event) throws JavaModelException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();
		Object element = selection.getFirstElement();
		System.out.println(element.getClass().getName());
		if(element.getClass().getName().equals("org.eclipse.jdt.internal.core.JavaProject")) {
			IJavaProject javaProject = (IJavaProject) element;
			ClassLoader cl = javaProject.getClass().getClassLoader();
			JOptionPane.showMessageDialog(null,cl.getClass());
			try {
				Class<?> c = ClassLoader.class.forName("RetroTest");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				JOptionPane.showMessageDialog(null,javaProject.getPackageFragmentRoots()[0].getElementName());
			} catch(HeadlessException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void getClassPathFromProject(ExecutionEvent event) throws CoreException, MalformedURLException, HeadlessException, ClassNotFoundException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();
		Object firstElement = selection.getFirstElement();
		IJavaProject project = (IJavaProject) firstElement;
		String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(project);
		
		List<URL> urlList = new ArrayList<URL>();
		for (int i = 0; i < classPathEntries.length; i++) {
		 String entry = classPathEntries[i];
		 IPath path = new Path(entry);
		 URL url = url = path.toFile().toURI().toURL();
		 urlList.add(url);
		}
		ClassLoader parentClassLoader = project.getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);
		Class<?> c = classLoader.loadClass("magroun.hamza.coucou.RetroTest");
		JOptionPane.showMessageDialog(null,c.getName());
		//List<URLClassLoader> loaders = new ArrayList<URLClassLoader>();
		//loaders.add(getProjectClassLoader(project));
		}
	
	
	
	
	
	
	
	
	
	
	public void HandlePath(ExecutionEvent event) {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();
		if (selection == null) {
			//return null;
		}
		
		Object[] tab = selection.toArray();
		JOptionPane.showMessageDialog(null,tab.length);
		for(int i=0;i<tab.length;i++) {
			System.out.println(tab[i].getClass().getName());
		}
		Arrays.fill(tab, null);
		Object firstElement = selection.getFirstElement();
		if(firstElement.getClass().getName() == "org.eclipse.jdt.internal.core.CompilationUnit") {
			ICompilationUnit javaFile = (ICompilationUnit) firstElement;
			try {
				JOptionPane.showMessageDialog(null,javaFile.getElementName() + "\n" + javaFile.getPath());
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		} else if(firstElement.getClass().getName() == "org.eclipse.core.internal.resources.File") {
			IFile jarFile = (IFile) firstElement;
			try {
				JOptionPane.showMessageDialog(null,jarFile.getName() + "\n" + jarFile.getProject() + "\n" + jarFile.getLocation());
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