/*
 * 迁移注记（2026-08-12）：本文件源自旧版 kunlun 的 data.fill 框架（依赖 kunlun.data.fill.DataFiller / FieldConfig /
 * kunlun.core.function.Function / kunlun.core.function.BiConsumer / kunlun.util.ObjUtil 等），
 * 这些 API 在 code-store 锁定的 kunlun 版本（io.github.kahle23:kunlun:1.0.0.20240217.beta）中已不存在，无法解析，故整类注释。
 * 如需启用，需在 code-store 内自建 data.fill 框架，或改用 store.code.data 现有体系。
 *
 * ----- 以下为原始迁移代码（整类注释）-----
package store.code.data.fill.lambda;

import kunlun.core.function.BiConsumer;
import kunlun.core.function.Function;
import kunlun.data.Array;
import kunlun.data.fill.DataFiller;
import kunlun.util.CollUtil;
import kunlun.util.MapUtil;
import kunlun.util.ObjUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static kunlun.common.constant.Numbers.ZERO;
import static kunlun.util.Assert.notEmpty;
import static kunlun.util.Assert.notNull;
import static kunlun.util.ObjUtil.cast;

/**
 * ClassicFiller
 * @author Kahle
 * /
public class LambdaFiller<T, T1> implements DataFiller<LambdaFillCfg<T, T1>> {

    @Override
    public void fill(LambdaFillCfg<T, T1> config) {
        notNull(config, "Parameter \"config\" must not null. ");
        // If data is null or field configs is empty, logical end.
        if (CollUtil.isEmpty(config.getDataConfigs())) { return; }
        if (ObjUtil.isEmpty(config.getData())) { return; }
        // Convert the data.
        Collection<T> data = config.getData();
        if (CollUtil.isEmpty(data)) { return; }
        // Extract the data to be queried.
        Map<LambdaDataCfg<T, T1>, Collection<Object>> conditionsMap =
                new LinkedHashMap<LambdaDataCfg<T, T1>, Collection<Object>>();
        for (T datum : data) {
            for (LambdaDataCfg<T, T1> dataConfig : config.getDataConfigs()) {
                for (FieldCfg<T, T1> fieldConfig : dataConfig.getFieldConfigs()) {
                    if (fieldConfig == null) { continue; }
                    Object value = CollUtil.getFirst(fieldConfig.getQueryFields()).apply(datum);
                    if (value == null) { continue; }
                    Collection<Object> coll = conditionsMap.get(dataConfig);
                    if (coll == null) {
                        conditionsMap.put(dataConfig, coll = new Array());
                    }
                    coll.add(value);
                }
            }
        }
        // Query data.
        for (Map.Entry<LambdaDataCfg<T, T1>, Collection<Object>> entry : conditionsMap.entrySet()) {
            Collection<Object> conditions = entry.getValue();
            LambdaDataCfg<T, T1> dataConfig = entry.getKey();
            // Fill data.
            doFill(dataConfig, data, dataConfig.getDataSupplier().apply(conditions));
        }
    }

    /**
     * doFill
     * @param cfg cfg
     * @param inputs inputs
     * @param data data
     * /
    protected void doFill(LambdaDataCfg<T, T1> cfg, Collection<T> inputs,
                          Map<String, T1> data) {
        // data validation.
        if (CollUtil.isEmpty(inputs)) { return; }
        if (MapUtil.isEmpty(data)) { return; }
        // fill data.
        for (T input : inputs) {
            for (FieldCfg<T, T1> fieldCfg : cfg.getFieldConfigs()) {
                Object value = CollUtil.getFirst(fieldCfg.getQueryFields()).apply(input);
                if (value == null) { continue; }
                T1 obj = data.get(String.valueOf(value));
                if (obj == null) { continue; }
                Object dataVal = fieldCfg.getDataField().apply(obj);
                if (dataVal == null) { continue; }
                fieldCfg.getFillField().accept(input, dataVal);
            }
        }
    }

    /**
     * The data filling field configuration.
     * @author Kahle
     * /
    public static class FieldCfg<T, T1> implements FieldConfig, Serializable {
        /**
         * The query field.
         * /
        private Collection<Function<T, Object>> queryFields;
        /**
         * The fill field.
         * /
        private BiConsumer<T, Object> fillField;
        /**
         * The data field.
         * /
        private Function<T1, Object> dataField;

        public FieldCfg(Collection<Function<T, Object>> queryFields, BiConsumer<T, ?> fillField, Function<T1, Object> dataField) {
            this.setQueryFields(queryFields);
            this.setFillField(fillField);
            this.setDataField(dataField);
        }

        public FieldCfg(Function<T, Object> queryField, BiConsumer<T, ?> fillField, Function<T1, Object> dataField) {
            this.setQueryFields(singletonList(notNull(queryField)));
            this.setFillField(fillField);
            this.setDataField(dataField);
        }

        public FieldCfg() {

        }

        @Override
        public Collection<Function<T, Object>> getQueryFields() {

            return queryFields;
        }

        public void setQueryFields(Collection<Function<T, Object>> queryFields) {

            this.queryFields = notEmpty(queryFields);
        }

        @Override
        public BiConsumer<T, Object> getFillField() {

            return fillField;
        }

        public void setFillField(BiConsumer<T, ?> fillField) {

            this.fillField = cast(notNull(fillField));
        }

        @Override
        public Function<T1, Object> getDataField() {

            return dataField;
        }

        public void setDataField(Function<T1, Object> dataField) {

            this.dataField = notNull(dataField);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) { return true; }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            //noinspection unchecked
            FieldCfg<T, T1> that = (FieldCfg<T, T1>) object;
            if (queryFields != null ? !queryFields.equals(that.queryFields) : that.queryFields != null) {
                return false;
            }
            if (fillField != null ? !fillField.equals(that.fillField) : that.fillField != null) {
                return false;
            }
            return dataField != null ? dataField.equals(that.dataField) : that.dataField == null;
        }

        @Override
        public int hashCode() {
            int result = queryFields != null ? queryFields.hashCode() : ZERO;
            result = 31 * result + (fillField != null ? fillField.hashCode() : ZERO);
            result = 31 * result + (dataField != null ? dataField.hashCode() : ZERO);
            return result;
        }
    }

}
 */