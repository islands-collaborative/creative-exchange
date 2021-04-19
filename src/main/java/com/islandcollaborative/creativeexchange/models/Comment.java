package com.islandcollaborative.creativeexchange.models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

}
