# IDATT2003_Boardgame_group30

A JavaFX-based desktop application that supports multiple board game variants. Developed as part of the IDATT2003 course.

## Features

- **Classic Snakes and Ladders**
- **Simplified Monopoly**
- **User customization**:
  - Choose number of players
  - Select board size
  - Upload a custom board using a JSON file
  - Select number of dice
- Special tile actions such as:
  - **Portals** – teleport players to a random tile (Snakes and Ladders)
  - **GoToJail** – sends a player to jail (Simplified Monopoly)
- Modular and extensible architecture
- Designed for easy addition of new game types
- Emphasis on high cohesion and low coupling


## Requirements

- JavaFX SDK
- Maven

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/alicetindra/IDATT2003_Boardgame_group30.git
cd IDATT2003_Boardgame_group30
```

2. Run the application using Maven:
```bash
mvn javafx:run
```

## Testing
You can run tests using:
  ```bash
    mvn test
  ```
## Limitations
- Gameplay is local only — no networked play.
  
## Project Structure

``` 
IDATT2003_Boardgame_group30/
├── src/
│   ├── main/
│   │   ├── java/edu/ntnu/idatt2003/boardgame
│   │   │   ├── Controller/       
│   │   │   ├── Model/actions          
│   │   │   ├── Observer/            
│   │   │   ├── View/      
│   │   │   └── readers/
|   |   |   └── writers/
│   │   └── resources/
|   |       └── images/          
│   └── test/                    
├── pom.xml                    
├── README.md                   
└── .gitignore
...                 

```
## Authors

Group 30 – IDATT2003 Spring 2025
