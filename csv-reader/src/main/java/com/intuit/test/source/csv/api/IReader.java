package com.intuit.test.source.csv.api;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 *  This interface declares functionality to read content from a data source
 * @param <T>
 */
public interface IReader<T> {

    Map<String, T> readContent() throws Exception;



}
