// IRandomService.aidl
package com.example.lab16_kt;

// Declare any non-default types here with import statements

interface IRandomService {
   double getRandom(in long seed);
}