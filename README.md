**BookWorm - A Smart Library Management App**

BookWorm is a modern Android application built to help users manage a personal or small library catalog with ease. It leverages the latest Android technologies to provide a fast, intuitive, and visually rich user experience. From adding books with OCR to managing their borrowing status, BookWorm is your personal digital librarian.

This project is built with a 100% Kotlin, single-activity architecture, using Jetpack Compose for the UI and following the official Google-recommended MVVM pattern.

**Core Features**
**1. Visual Book Catalog with Live Search**

The main screen provides a beautiful and interactive list of all your books, equipped with powerful search capabilities.

Visual-First Design: Each book is displayed in a card with its cover image thumbnail, title, and author, making your collection easy to browse at a glance.

Live Search & Dynamic Filtering: Instantly find any book with a powerful search bar. As you type, the list dynamically filters in real-time to show books matching the title or author, providing immediate results just like a modern search engine.

Effortless Scrolling: Built with LazyColumn for smooth and efficient performance, even with a large number of books.

**2. Smart Book Addition with OCR**

Adding new books to your library is faster than ever.

Manual Entry: A simple and clean form to manually type in a book's title and author.

Image from Gallery: Add a book cover by selecting an image directly from your phone's gallery.

Scan with Camera (OCR): The flagship feature. Simply take a picture of a book's cover, and the app will:

Automatically extract the title and author using on-device Machine Learning (Google ML Kit).

Set the captured photo as the book's cover image.

**3. Deliberate & Safe Deletion**

Removing a book is a straightforward and safe process, preventing accidental deletions.

Long-Press to Delete: To delete a book, simply press and hold its card in the main list.

Confirmation Dialog: A confirmation dialog will appear, asking if you are sure you want to delete the book. This two-step process ensures you never remove an entry by mistake.

**4. Interactive Status Management**

Keep track of which books are available and which are borrowed with a dedicated and dynamic system.

Read-Only Status on Forms: A book's status cannot be changed while adding or editing its details, ensuring a consistent workflow. New books are always "Available" by default.

Dynamic Action Button: In the book detail view, a single button intelligently switches between "Borrow Book" and "Return Book" based on the current status.

Reactive UI: When you borrow or return a book, the UI updates instantly without any screen reloads, thanks to a reactive data flow from the database to the UI.

**5. Detailed Book View & Editing**

View all details of a book in one place and easily make corrections.

Full-Size Cover: The detail screen prominently displays the book's cover image.

Easy Editing: A clear "Edit" option allows you to navigate to a dedicated screen to update a book's cover image, title, or author.

**6. Robust and Modern Architecture**

The app is built using a modern, scalable, and maintainable tech stack.

100% Kotlin & Jetpack Compose: The entire UI is built with Jetpack Compose, resulting in more concise, declarative, and powerful UI code.

MVVM Architecture: Follows the Model-View-ViewModel pattern to separate UI from business logic.

Room Database: For robust and efficient on-device data persistence.

Kotlin Coroutines & Flow: For managing asynchronous operations and creating reactive data streams that power the app's dynamic UI.

Hilt for Dependency Injection: Manages dependencies and simplifies the overall architecture.

Coil for Image Loading: A lightweight and efficient image loading library optimized for Kotlin and Jetpack Compose.
