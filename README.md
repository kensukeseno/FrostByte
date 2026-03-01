src/main/java/com/example/frostbyte
├── MainActivity.kt                 // Entry point of the app; sets up Compose and navigation
├── navigation/                     // Handles all app navigation
│     └── AppNavGraph.kt            // Defines NavHost, composable routes, and arguments
│
├── ui/                             // All UI-related code (screens & components)
│     ├── home/                     // Home screen UI
│     │     └── HomeScreen.kt       // Shows list of task lists, FAB to add new lists
│     ├── list/                     // List screen UI
│     │     └── ListScreen.kt       // Shows tasks for a selected list
│     ├── task/                     // Task screen UI
│     │     └── TaskScreen.kt       // Add/Edit a task with parameters
│     ├── results/                  // Results screen UI
│     │     └── ResultsScreen.kt    // Shows tasks categorized as Do, Schedule, Delegate, Delete
│     ├── components/               // Reusable UI components
│     │     ├── ListCard.kt         // Card to display a list in HomeScreen
│     │     ├── TaskItem.kt         // Composable for each task item
│     │     └── CategoryChip.kt     // Small UI element showing task category (Do, Schedule…)
│
├── viewmodel/                      // ViewModels (UI logic & state management)
│     ├── HomeViewModel.kt          // Provides lists to HomeScreen
│     ├── ListViewModel.kt          // Provides tasks for ListScreen
│     └── TaskViewModel.kt          // Handles adding/updating tasks, categorization logic
│
├── data/                           // Data layer (Room DB + repository)
│     ├── entity/                   // Room database entities
│     │     ├── TaskEntity.kt       // Task table representation
│     │     └── ListEntity.kt       // List table representation
│     ├── dao/                      // Data Access Objects
│     │     ├── TaskDao.kt          // Queries for TaskEntity
│     │     └── ListDao.kt          // Queries for ListEntity
│     ├── AppDatabase.kt            // Room database setup
│     └── TaskRepository.kt         // Abstracts DB operations for ViewModels
│
├── domain/                         // Business logic, independent of UI
│     └── EisenhowerEngine.kt       // Categorizes tasks into Do/Schedule/Delegate/Delete
│
└── util/                           // Utility files
└── Constants.kt              // App-wide constants (e.g., categories, default values)