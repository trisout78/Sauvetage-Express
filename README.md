# Sauvetage-Express

Sauvetage-Express est un plugin Minecraft développé pour offrir un jeu rapide et intense où les joueurs doivent protéger un joueur sélectionné tout en évitant les autres risques. Ce projet utilise l'API Bukkit pour Minecraft.

## Fonctionnalités

- **Sélection aléatoire de joueur** : Un joueur est sélectionné aléatoirement pour être protégé.
- **Invulnérabilité temporaire** : Le joueur sélectionné est invulnérable pendant une durée définie.
- **Téléportation** : Les autres joueurs peuvent se téléporter vers le joueur sélectionné en utilisant une boussole.
- **Gestion des commandes** : Commandes pour démarrer, arrêter le jeu et contourner l'invulnérabilité.
- **Barre de boss** : Indicateur visuel de la progression de l'invulnérabilité et du temps restant pour le jeu.

## Prérequis

- Minecraft serveur version 1.19 ou supérieur
- Plugin EterEvents

## Installation

1. Téléchargez le fichier JAR du plugin depuis la section des releases.
2. Placez le fichier JAR dans le dossier `plugins` de votre serveur Minecraft.
3. Démarrez (ou redémarrez) votre serveur Minecraft pour charger le plugin.

## Configuration

Vous pouvez configurer les paramètres du plugin dans le fichier `config.yml` situé dans le dossier `plugins/SauvetageExpress`. Voici les paramètres disponibles :

```yaml
timers:
  invulnerability: 300 # Temps d'invulnérabilité en secondes
  gameDuration: 600 # Durée du jeu en secondes
eterevents:
  stopwitheterevents: true # Arrêter le jeu avec EterEvents
```

## Commandes

- `/sauvetageexpress start` : Démarrer le jeu.
- `/sauvetageexpress stop` : Arrêter le jeu.
- `/sauvetageexpress bypassiv` : Contourner l'invulnérabilité du joueur sélectionné.

## Permissions

- `sauvetageexpress.command` : Permission pour utiliser les commandes du plugin.

## Développement

### Structure du Projet

- `src/main/java/fr/etercube/sauvetageexpress/Main.java` : Classe principale du plugin.
- `src/main/java/fr/etercube/sauvetageexpress/SauvetageExpressCommand.java` : Gestion des commandes du plugin.
- `src/main/java/fr/etercube/sauvetageexpress/utils/ConvertSecondToMinutesAndSeconds.java` : Utilitaire pour convertir les secondes en minutes et secondes.

### Contribuer

1. Forkez le projet.
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/AmazingFeature`).
3. Committez vos changements (`git commit -m 'Add some AmazingFeature'`).
4. Poussez votre branche (`git push origin feature/AmazingFeature`).
5. Ouvrez une Pull Request.

## Licence

Ce projet est sous licence GPL-3.0. Voir le fichier [LICENSE](LICENSE) pour plus de détails.
