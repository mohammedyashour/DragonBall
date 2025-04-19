# Dragon Ball Explorer 🐉

![Dragon Ball Logo]("")

A Kotlin application that interacts with the Dragon Ball API to display characters, planets, and play quiz games with caching functionality.

## Features ✨

- **Character Explorer**: Browse all Dragon Ball characters with pagination
- **Planet Viewer**: Discover planets from the Dragon Ball universe
- **Quiz Game**: Test your knowledge with character guessing game
- **Dual Language**: English/Arabic support
- **Offline Caching**: Local data persistence with Room database
- **Modern Architecture**: Clean separation of concerns

## Tech Stack 🛠️

- **Kotlin** - Primary programming language
- **Ktor Client** - HTTP client for API calls
- **Room Database** - Local caching layer
- **Coroutines** - Asynchronous operations
- **Clean Architecture** - Presentation/Domain/Data layers

## API Endpoints 🌐

| Endpoint | Description |
|----------|-------------|
| `/characters` | Get all characters |
| `/characters/{id}` | Get single character |
| `/planets` | Get all planets |
| `/planets/{id}` | Get single planet |

## Installation ⚙️

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/dragonball-explorer.git
