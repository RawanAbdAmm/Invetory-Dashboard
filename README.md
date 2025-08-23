# 📦 Inventory Dashboard (Kotlin · MVVM · Clean Architecture)

A lightweight Android app that fetches **Materials** and **Quantities** from an HTTP API, combines them, and stores them in **Room** for reliable **offline** use. The main screen shows a neat, searchable list/grid with quantity and last sync time.

> Built with **Android Studio Narwhal | 2025.1.2 Patch 1**

---

## 🚀 Tech Stack
- **Language:** Kotlin
- **Architecture:** Clean Architecture + MVVM
- **Networking:** Retrofit (+ OkHttp)
- **Concurrency:** Coroutines + Flow
- **Database:** Room
- **UI:** XML (RecyclerView, SwipeRefresh, Snackbar/Toast)

---

## 🧩 Core Features
- Fetch latest materials & quantities and save to Room **in one transaction**.
- **Offline-first:** load cached data when no internet; show a clear **offline** banner.
- Unified list shows: **name**, **category**, **quantity**, **last sync time**.
- **Search / filter** by name or category, plus **pull-to-refresh**.
- **Graceful error handling:** Snackbar/Toast + **Retry** action (no crashes).

---

## 📂 Structure
```
data/
 ├─ remote/        # Retrofit DTOs & ApiService
 ├─ local/         # Room entities & DAO
 └─ repository/    # Repository implementation

domain/
 ├─ model/         # Core models
 ├─ repository/    # Interfaces
 └─ usecase/       # Interactors

presentation/
 ├─ viewmodel/     # ViewModels (Flows/State)
 ├─ adapter/       # RecyclerView adapters
 └─ ui/            # Activities/Fragments, XML layouts
```

---

## 🖼️ UI States
- **Loading** (progress)
- **Empty** (no results)
- **Error** (message + Retry)
- **Offline** (banner)
- Main screen: list/grid of items with name, category, quantity, **last sync**.

---

## 🔧 Setup & Run
1. Open in **Android Studio Narwhal 2025.1.2 Patch 1** or newer.
2. Set API base URL in `ApiService` (and `network_security_config` if needed).
3. Run. Pull-to-refresh to sync. Offline shows cached data + banner.

---

## 📦 APK (Optional)
Place your release APK at:
```
app/release/app-release.apk
```

---

## 📝 Notes
- Last sync timestamp is updated after a successful refresh and displayed in the UI.
- Network failures are handled with a retryable Snackbar/Toast; the app remains stable offline.


