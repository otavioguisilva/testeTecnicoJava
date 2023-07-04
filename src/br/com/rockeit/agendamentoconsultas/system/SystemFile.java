package br.com.rockeit.agendamentoconsultas.system;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import br.com.rockeit.agendamentoconsultas.application.AgendamentoConsultas;


public class SystemFile  {

	private SystemFile() {
		super();
	}
	
	public static String getProjectFolder() {
		
		try {
			String baseResource = getResourceFolder(AgendamentoConsultas.class);

			return baseResource;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getSourceFolder(AgendamentoConsultas.class);
	}
	
	public static String getResourceFolder(Class<?> classR) {
		return classR.getResource("/").getFile();
	}
	
	public static String getSourceFolder(Class<?> classR) {

		String base = null;
		try {
			base = classR.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		if (base == null) {
			base = "/";

		} else {
			if (base.startsWith(File.separator)) {
				base = base.substring(1);
			}

			if (base.endsWith(".jar")) {
				File file = new File(base);

				base = file.getParent();
			}
		}
		return base;
	}

	public static String getApplicationImageFolder() {
		return "/br/com/rockeit/agendamentoconsultas/images";
	}

	public static String getApplicationImage(String imagem) {
		return getApplicationImageFolder() + "/" + imagem;
	}

	public static File getImageFolder() {

		File file = new File(getProjectFolder(), "images");

		if (!file.exists()) {
			file.mkdir();
		}

		return file;
	}

	public static DirectoryStream<Path> getImages(Path path) throws IOException {

		return Files.newDirectoryStream(path, (arq) -> {
			String nomeFile = arq.toFile().getName().toLowerCase();

			return nomeFile.endsWith(".jpg") || nomeFile.endsWith(".jpeg") || nomeFile.endsWith(".png")
					|| nomeFile.endsWith(".gif");

		});
	}

	public static File getPastaLogo() {

		File file = new File(getImageFolder(), "logo");

		if (!file.exists()) {
			file.mkdir();
		}

		return file;
	}

	public static File getLogo() {

		File logo = new File(getPastaLogo(), "logo.png");

		return logo;
	}

}
