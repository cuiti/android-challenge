# Game of Thrones for Android Challenge

### App was developed using:
* Android Studio 3.5.2
* Kotlin 1.3.50
* Android Gradle plugin 3.5.2

Since image URLs are not working (Firebase returning status 4xx), the error fallback is handled with Glide. The problem with this approach is that Glide needs to wait until the server responds with an error before showing the fallback image, and that takes a second or two, which could mess with the animations. Given that one of the goals of this demo is to showcase those animations, I set up Glide to replace all images with an existing one, without waiting for the error.

### Added features

1. Search characters by name in the characters list
2. Create a list of characters by house, accessing to it by clicking a house image in the list of houses
3. Capability to work offline using database
4. Refactored code to MVP pattern with RxJava. Added Dependency Injection
5. Tests fo r the main logic and a high level flow.
6. Added shared element transitions between list and detail
7. Added parallax effect into detail page


#License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[ScreenshotListCharacters]: ./art/ScreenshotListCharacters.png
[ScreenshotListHouses]: ./art/ScreenshotListHouses.png
[ScreenshotDetail]: ./art/ScreenshotDetail.png
[ActivityLink]: http://developer.android.com/intl/es/guide/components/activities.html
[FragmentLink]: http://developer.android.com/intl/es/guide/components/fragments.html
[GameOfThronesLink]: http://www.imdb.com/title/tt0944947/
[ViewPagerLink]: http://developer.android.com/intl/es/training/animation/screen-slide.html
