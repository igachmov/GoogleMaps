package com.example.ivan.recyclerviewmap.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivan on 9/13/2017.
 */

public interface Gasstations extends Serializable {

    int num_all_results();
    int num_results();
    List<Gasstation> gasstations();
}