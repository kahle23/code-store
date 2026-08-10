package store.code.data.fill.lambda;

import kunlun.data.fill.DataFiller;

import java.util.ArrayList;
import java.util.Collection;

import static kunlun.util.Assert.notEmpty;
import static kunlun.util.Assert.notNull;

/**
 * LambdaFillCfg
 * @author Kahle
 */
public class LambdaFillCfg<T, T1> implements DataFiller.FillConfig {

    public static <T, T1> LambdaFillCfg<T, T1> of(Collection<T> data) {

        return new LambdaFillCfg<T, T1>(data);
    }

    private final Collection<LambdaDataCfg<T, T1>> dataConfigs = new ArrayList<LambdaDataCfg<T, T1>>();
    private Collection<T> data;

    public LambdaFillCfg(Collection<T> data) {

        this.setData(data);
    }

    @Override
    public Collection<LambdaDataCfg<T, T1>> getDataConfigs() {

        return dataConfigs;
    }

    public LambdaFillCfg<T, T1> addDataConfigs(Collection<LambdaDataCfg<T, T1>> dataConfigs) {
        this.dataConfigs.addAll(notEmpty(dataConfigs));
        return this;
    }

    public LambdaFillCfg<T, T1> addDataConfig(LambdaDataCfg<T, T1> dataConfig) {
        this.dataConfigs.add(notNull(dataConfig));
        return this;
    }

    public LambdaFillCfg<T, T1> clearDataConfigs() {
        this.dataConfigs.clear();
        return this;
    }

    @Override
    public Collection<T> getData() {

        return data;
    }

    public LambdaFillCfg<T, T1> setData(Collection<T> data) {
        this.data = notEmpty(data);
        return this;
    }

    public void fill() {

        new LambdaFiller<T, T1>().fill(this);
    }

}
