package com.dorifto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dorifto.GameWorld;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Configuration du contexte de rendu
		config.useGL30 = true;							// VERSION DE OPENGL

		// Configuration de la fenetre
		config.title = "Game jam bitches";				// TITRE DE LA FENETRE
		config.width = 1920;							// LARGEUR DE LA FENETRE
		config.height = 1010;							// HAUTEUR DE LA FENETRE
		config.resizable = false;						// FENETRE REDIMENSIONNABLE
		config.fullscreen = true;						// MODE FULLSCREEN

		new LwjglApplication(new GameWorld(), config);	// LANCE NOTRE APPLICATION
	}
}
