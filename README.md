src/main/java/com/example/frostbyte</br>
├── MainActivity.kt                 // Entry point of the app; sets up Compose and navigation</br>
├── navigation/                     // Handles all app navigation</br>
│     └── AppNavGraph.kt            // Defines NavHost, composable routes, and arguments</br>
│</br>
├── ui/                             // All UI-related code (screens, components, theme)</br>
│     ├── theme/                    // Material 3 theming files</br>
│     │     ├── Color.kt            // Defines app colors</br>
│     │     ├── Shape.kt            // Defines shapes (corners, cutouts) for components</br>
│     │     ├── Theme.kt            // Combines colors, typography, and shapes; provides MaterialTheme</br>
│     │     └── Type.kt             // Defines typography styles</br>
│     │</br>
│     ├── home/                     // Home screen UI</br>
│     │     └── HomeScreen.kt       // Shows list of task lists, FAB to add new lists</br>
│     ├── list/                     // List screen UI</br>
│     │     └── ListScreen.kt       // Shows tasks for a selected list</br>
│     ├── task/                     // Task screen UI</br>
│     │     └── TaskScreen.kt       // Add/Edit a task with parameters</br>
│     ├── results/                  // Results screen UI</br>
│     │     └── ResultsScreen.kt    // Shows tasks categorized as Do, Schedule, Delegate, Delete</br>
│     ├── components/               // Reusable UI components</br>
│     │     ├── ListCard.kt         // Card to display a list in HomeScreen</br>
│     │     ├── TaskItem.kt         // Composable for each task item</br>
│     │     └── CategoryChip.kt     // Small UI element showing task category (Do, Schedule…)</br>
│</br>
├── viewmodel/                      // ViewModels (UI logic & state management)</br>
│     ├── HomeViewModel.kt          // Provides lists to HomeScreen</br>
│     ├── ListViewModel.kt          // Provides tasks for ListScreen</br>
│     └── TaskViewModel.kt          // Handles adding/updating tasks, categorization logic</br>
│</br>
├── data/                           // Data layer (Room DB + repository)</br>
│     ├── entity/                   // Room database entities</br>
│     │     ├── TaskEntity.kt       // Task table representation</br>
│     │     └── ListEntity.kt       // List table representation</br>
│     ├── dao/                      // Data Access Objects</br>
│     │     ├── TaskDao.kt          // Queries for TaskEntity</br>
│     │     └── ListDao.kt          // Queries for ListEntity</br>
│     ├── AppDatabase.kt            // Room database setup</br>
│     └── TaskRepository.kt         // Abstracts DB operations for ViewModels</br>
│</br>
├── domain/                         // Business logic, independent of UI</br>
│     └── EisenhowerEngine.kt       // Categorizes tasks into Do/Schedule/Delegate/Delete</br>
│</br>
└── util/                           // Utility files</br>
└── Constants.kt              // App-wide constants (e.g., categories, default values)</br>
