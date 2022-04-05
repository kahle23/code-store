package store.code.dict.way2;

import artoria.common.Loader;

import java.util.Map;

public interface PropertyLoader extends Loader {

    Map<String, Map<String, Object>> loadAll();

}
