package com.gradle.evive;

import java.util.AbstractMap;
import java.util.ArrayList;

public record Order(String type, ArrayList<PairStringInt> itemAndQuant, String message) {}
