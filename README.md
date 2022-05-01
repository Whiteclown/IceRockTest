# IceRockTest
Приложение для просмотра GitHub репозиториев.

# Архитектура
>app -> главный модуль  
core -> вспомогательный модуль  
buildSrc -> build логика  
features ->  Директория с фичами  
>>authorization -> Экран авторизации  
detailinfo -> Экран с детальной информацией о репозитории  
repositorieslist -> Экран со списком репозиториев  
splash -> Загрузочный экран  
>
>shared -> Директория с модулями, которые могут использоваться несколькими модулями  
>>session -> Модуль для работы с github api  

# Скриншоты
<img src="https://user-images.githubusercontent.com/59654044/166139761-8daf469a-f51c-4c60-bc8d-7039fcb15f6e.png" width="250" height="500"> <img src="https://user-images.githubusercontent.com/59654044/166139931-4c4b98e0-720d-4da1-ae70-79e3638872d0.png" width="250" height="500"> <img src="https://user-images.githubusercontent.com/59654044/166140988-625c52b8-dafe-437e-9ab2-8456e4e32e91.png" width="250" height="500">
