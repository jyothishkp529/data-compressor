# data-compressor
**data-compressor** is a scala based utility to compress data. The base version contains 
feature to compress non-numeric String data.

## Approach
Each non-numeric characters appears in serial as a substring in the input string will be replaced with number of 
occurrence in that continues series along with the character itself. The compression will be applied if a character 
repeatedly appears in sequence at-least minimum number of occurrence, which is driven by consumer input. 

#### *Example*
 |Input         | Minimum occurancy|Output        |
 ---------------|------------------|--------------|
 |ABBCDD        | 2                |   A2BC2D     |
 |ABBBCCDDD     | 3                |   A3BCC3D    |
 |ABBBCCDDD     | 4                |   ABBBCCDDD  |
 |ABBBBCCCDDDDD | 4                |   A4BCCC5D   |


## Assumptions
* The input string contains non-numeric characters only.
* The input is case sensitive; 'A' & 'a' treated as two characters.

## Technology Stack
* Scala
* ScalaTest
* SBT


## Usage Instructions
1. Build using sbt 
    ```sbt clean assembly```

2. Excute utility
    ```
    cd <path to data-compressor>/target/scala-2.12
    scala -cp data-compressor-assembly-1.0.0.jar data-compressor_2.12-1.0.0.jar --ip <input string> --mo <minOccurrence>
    ```
