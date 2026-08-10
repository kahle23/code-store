package store.code.storage.way3.support;

/*
 * 迁移注记：依赖 artoria.storage.support.ThreadLocalStorage 在当前 artoria 版本中不存在，且无外部引用，已整体注释
 */
// 
// import artoria.collect.ReferenceMap;
// import artoria.data.ReferenceType;
// import kunlun.storage.support.ThreadLocalStorage;
// import artoria.util.Assert;
// 
// import java.util.Map;
// 
// import static artoria.lang.ReferenceType.SOFT;
// import static artoria.lang.ReferenceType.WEAK;
// 
//  * Warning: If no value is assigned to a variable after the object is declared, it is put directly into storage,
//  * If GC occurs during execution, the values in the storage are lost.
//  * So you have to assign an object to a variable.
// public class ThreadLocalReferenceStorage extends ThreadLocalStorage {
//     private final ReferenceType referenceType;
// 
//     public ThreadLocalReferenceStorage(String name, ReferenceType referenceType) {
//         super(name);
//         Assert.notNull(referenceType, "Parameter \"referenceType\" must not null. ");
//         Assert.isTrue(SOFT.equals(referenceType) || WEAK.equals(referenceType),
//                 "Parameter \"referenceType\" must be soft reference or weak reference. ");
//         this.referenceType = referenceType;
//     }
// 
//     @Override
//     protected Map<Object, Object> buildBucket() {
// 
//         return new ReferenceMap<Object, Object>(referenceType);
//     }
// 
// }
