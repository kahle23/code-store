package store.code.data.fill.lambda;

import kunlun.core.function.BiConsumer;
import kunlun.core.function.Function;
import kunlun.data.fill.DataFiller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static kunlun.common.constant.Numbers.ZERO;
import static kunlun.data.fill.support.lambda.LambdaFiller.FieldCfg;
import static kunlun.util.Assert.notEmpty;
import static kunlun.util.Assert.notNull;

/**
 * LambdaDataCfg
 * @author Kahle
 */
public class LambdaDataCfg<T, T1> implements DataFiller.DataConfig, Serializable {

    public static <T, T1> LambdaDataCfg<T, T1> of(Function<Collection<?>, Map<String, T1>> dataSupplier) {

        return new LambdaDataCfg<T, T1>(dataSupplier);
    }

    private final Function<Collection<?>, Map<String, T1>> dataSupplier;
    private Collection<FieldCfg<T, T1>> fieldConfigs = new ArrayList<FieldCfg<T, T1>>();

    public LambdaDataCfg(Function<Collection<?>, Map<String, T1>> dataSupplier) {

        this.dataSupplier = notNull(dataSupplier);
    }

    @Override
    public Collection<FieldCfg<T, T1>> getFieldConfigs() {

        return fieldConfigs;
    }

    public void setFieldConfigs(Collection<FieldCfg<T, T1>> fieldConfigs) {

        this.fieldConfigs = notNull(fieldConfigs);
    }

    @Override
    public Function<Collection<?>, Map<String, T1>> getDataSupplier() {

        return dataSupplier;
    }

    public LambdaDataCfg<T, T1> addFieldConfig(Function<T, Object> queryField, BiConsumer<T, ?> fillField, Function<T1, Object> dataField) {
        this.fieldConfigs.add(new FieldCfg<T, T1>(queryField, fillField, dataField));
        return this;
    }

    public LambdaDataCfg<T, T1> addFieldConfigs(List<FieldCfg<T, T1>> fieldConfigs) {
        this.fieldConfigs.addAll(notEmpty(fieldConfigs));
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) { return true; }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        //noinspection unchecked
        LambdaDataCfg<T, T1> that = (LambdaDataCfg<T, T1>) object;
        if (dataSupplier != null ? !dataSupplier.equals(that.dataSupplier) : that.dataSupplier != null) {
            return false;
        }
        return fieldConfigs != null ? fieldConfigs.equals(that.fieldConfigs) : that.fieldConfigs == null;
    }

    @Override
    public int hashCode() {
        int result = dataSupplier != null ? dataSupplier.hashCode() : ZERO;
        result = 31 * result + (fieldConfigs != null ? fieldConfigs.hashCode() : ZERO);
        return result;
    }

}
