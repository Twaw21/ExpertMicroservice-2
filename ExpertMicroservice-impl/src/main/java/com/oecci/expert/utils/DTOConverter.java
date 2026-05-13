package com.oecci.expert.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Class: QuotingService
 * Convertisseur
 * @Developper: TKA <arouna@radiscode.com>
 * Created at : 09/12/2022
 */
public final class DTOConverter {
    private static final Log _log = LogFactoryUtil.getLog(DTOConverter.class);

    public DTOConverter() {

    }

    private static ObjectMapper createMapperObjectInstance() {
        ObjectMapper oMapper = new ObjectMapper();
        //oMapper.registerModule(new JavaTimeModule());
        oMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //oMapper.configOverride(Instant.class).setFormat(JsonFormat.Value.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault()));
        oMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        oMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        return oMapper;
    }

    public static <S,D> D convertObject(S source, Class<D> destination ) {
        if(source == null)
            return null;

        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        return oMapper.convertValue(source, destination);
    }

    public static <D,E> List<D> convertObject(Collection<E> entityCollection, Class<D> domainClass) {
        if(entityCollection == null)
            return null;

        return (List)DTOConverter.convertObject(List.class, entityCollection, domainClass);
    }

    private static <D,E, K extends Collection> K convertObject(Class<K> collectionClass, Collection<E> entityCollection, Class<D> domainClass ) {
        Collection<D> domainList = List.class.isAssignableFrom(collectionClass) ? new ArrayList() : new HashSet();
        Iterator var5 = entityCollection.iterator();

        while(var5.hasNext()) {
            E _entity = (E) var5.next();
            ((Collection)domainList).add(DTOConverter.convertObject(_entity,domainClass));
        }

        final K domainList1 = (K) domainList;
        return domainList1;

        //ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        //return oMapper.convertValue(source, destination);
    }

    public static String toJsonFromMap(Map<String, Object> mapObj) throws JsonProcessingException {
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        return oMapper.writeValueAsString(mapObj);
    }

    // ===========================
    // ======== J S O N ==========
    // ===========================
    public static <S> String toJsonStringFromObj(S source) throws JsonProcessingException {
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        ObjectWriter ow = oMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(source);
    }

    public static Map<String, Object> toMapFromJsonString(String str) throws JsonProcessingException {
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        return oMapper.readValue(str,Map.class);
    }

    public static <S> byte[] toBytesFromObj(S source) throws JsonProcessingException {
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        ObjectWriter ow = oMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsBytes(source);
    }

    // ===========================
    // ======== N O D E ==========
    // ===========================
    public static ObjectNode convertToObjectNode(Object object){
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        return oMapper.valueToTree(object);
    }

    public static JsonNode convertToObjectNode(String str) throws JsonProcessingException {
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        return oMapper.readTree(str);
    }

    //=========================================================
    //==========ENTITY CONVERT TO DOMAIN / DOMAIN TO ENTITY====
    //=========================================================

    private static <T> T createInstance(Class<T> objClass) {
        try {
            return objClass.newInstance();
        } catch (InstantiationException var3) {
            throw new RuntimeException("Default constructor not found for " + objClass.getSimpleName(), var3);
        } catch (IllegalAccessException var4) {
            throw new RuntimeException("Make default constructor of " + objClass.getSimpleName() + " public", var4);
        }
    }

    //=========================================================
    //==========OBJECT CONVERT TO MAP / MAP TO OBJECT====
    //=========================================================

    /**
     * Convertir Object en Map
     * @param obj
     * @return
     */
    public static Map<String, Object> toMap(Object obj) {
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        return oMapper.convertValue(obj,Map.class);
    }

    /**
     * Convertir List Object en List Map
     * @param entityCollection
     * @param <D>
     * @param <E>
     * @return List Map
     */
    public static <D,E> List<D> toMap(Collection<E> entityCollection) {
        return (List)DTOConverter.toMap(List.class, entityCollection);
    }

    private static <D,E, K extends Collection> K toMap(Class<K> collectionClass, Collection<E> entityCollection) {
        Collection<D> mapList = List.class.isAssignableFrom(collectionClass) ? new ArrayList() : new HashSet();
        Iterator var5 = entityCollection.iterator();

        while(var5.hasNext()) {
            E _entity = (E) var5.next();
            ((Collection)mapList).add(DTOConverter.toMap(_entity));
        }

        final K domainList1 = (K) mapList;
        return domainList1;
    }

    /**
     * Convertir Object en Map
     * @param obj
     * @return
     */
    public static Map<String, Serializable> toMapSeriazable(Object obj) {
        if(obj == null) return null;
        ObjectMapper oMapper = DTOConverter.createMapperObjectInstance();
        Map<String, Serializable> map = oMapper.convertValue(obj,Map.class);
        map.values().removeIf(Objects::isNull);
        return map;
    }

    /**
     * Copy one Map to another map
     * @param original
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> copyMap(Map<K, V> original) {

        Map<K, V> second_Map = new HashMap<>();

        // Start the iteration and copy the Key and Value
        // for each Map to the other Map.
        for (Map.Entry<K, V> entry : original.entrySet()) {

            // using put method to copy one Map to Other
            second_Map.put(entry.getKey(),entry.getValue());
        }

        return second_Map;
    }

    public static <K, V> Map<K, V> copyMap(Map<K, V> original, Map<K, V> receipt) {
        Map<K, V> second_Map = new HashMap<>();
        // Start the iteration and copy the Key and Value
        // for each Map to the other Map.
        for (Map.Entry<K, V> entry : original.entrySet()) {
            if(receipt.containsKey(entry.getKey())) {
                second_Map.put(entry.getKey(),entry.getValue());
            }
            // using put method to copy one Map to Other
        }

        return second_Map;
    }

    public static <K, V> Map<K, V> copyMapNotNull(Map<K, V> original, Map<K, V> receipt) {
        Map<K, V> second_Map = new HashMap<>();
        // Start the iteration and copy the Key and Value
        // for each Map to the other Map.
        for (Map.Entry<K, V> entry : original.entrySet()) {
            if(receipt.containsKey(entry.getKey()) && entry.getValue() != null) {
                second_Map.put(entry.getKey(),entry.getValue());
            }
            // using put method to copy one Map to Other
        }

        return second_Map;
    }

    public static Map<String, Object> convertListToMap(List<String> strs) {
        Map<String, Object> _variable2Map = new HashMap<>();
        strs.forEach(s -> _variable2Map.put(s,""));
        return _variable2Map;
    }

    public static <K, V> void logObject(Map<K, V> original, String title) {
        String logStr = "";
        System.out.println(":::::::::::: "+title+" :::::::::::::");
        for (Map.Entry<K, V> entry : original.entrySet()) {
            logStr += String.format("%s : %s\n",entry.getKey(), entry.getValue());
        }
        System.out.println(logStr);
    }

    public static <K, V> void logObject(List<Map<K, V>> objList, String title) {
        String logStr = "";
        System.out.println(":::::::::::: "+title+" :::::::::::::");
        for(int i = 0; i < objList.size(); i++) {
            logStr =  String.format(" ----------- %s ------------ \n",i);
            System.out.println(logStr);
            Map<K, V> objMap = objList.get(i);
            for (Map.Entry<K, V> entry : objMap.entrySet()) {
                logStr = String.format("%s : %s", entry.getKey(), entry.getValue());
                System.out.println(logStr);
            }
        }
    }

    public static List<String> getProperties(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    public static List<Object> getPropertyValues(Object instance) {
        List<Object> values = new ArrayList<>();
        Class<?> clazz = instance.getClass();

        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true); // Permet d'accéder aux champs privés
                values.add(field.get(instance)); // Récupère la valeur du champ
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field values", e);
        }

        return values;
    }
}
