package com.example.ivan.recyclerviewmap.Service;

import java.io.Serializable;

/**
 * Created by xComputers on 14/06/2017.
 */

public interface Price extends Serializable{

    String fuel();
    String date();
    double price();
    String dimension();
}