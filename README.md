# sirius-tech

You can check the search algorithm in **BinarySearchUtil.kt** and the performance of searching is better than linear (n) for sure.
from my calculation for filter algorithm ≈ Log(n). and I also provide the unit test for the binary search as well. you can found it in **BinarySearchUtilTest.kt**


1. query = i times <br>
2. binary search to find first match = log(n) <br>
3. binary search to find left most = log(l) <br>
4. binary search to find right most = log(m) <br>

i is constant  <br>
l < n and m < n  <br> <br>

i(log(n) + log(l) + lon(m)) < 3i(log(n)) ≈ log(n) 


There is one third-party library that is included in the project. I decided to use Paging3. because It is easy to use for setting prefetch data.
before it comes to the point that requires to show cities data. and If we need to make its scalability. This lib can be connected with the database as well.
You can check for more information below the link I attached.

https://annchar.medium.com/android-paging-3-library-with-offset-and-limit-parameters-mvvm-livedata-and-coroutine-part1-5f85aa4fd29a<br><br>
https://www.youtube.com/watch?v=1cwqGOku2a4

https://user-images.githubusercontent.com/37785216/160248401-079deb5f-8c50-44ea-88b9-9acc9fcf1f9e.mp4

