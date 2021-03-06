package org.springside.modules.utils.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springside.modules.utils.collection.extend.SortedArrayList;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

/**
 * 关于List的工具集合，
 * 
 * 1. 常用函数(如是否为空)
 * 
 * 2. 便捷的构造函数(from guava)
 * 
 * 3. emptyList,singletonList,unmodifiedList (vis JDK Collection)
 * 
 * 4. sort/binarySearch/shuffle/reverse(via JDK Collection)
 * 
 * 5. ArrayList 与 Array的双向转换，包含Guava特有的原子类型的asList()
 * 
 * 6. 集合运算，包括两个List是否完全相等，两个List的交集，并集，from Commons Collecton）
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class ListUtil {

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(List<?> list) {
		return (list == null) || list.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(List<?> list) {
		return (list != null) && !(list.isEmpty());
	}

	/**
	 * 获取第一个元素, 如果List为空返回 null.
	 */
	public static <T> T getFirst(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取最后一个元素，如果List为空返回null.
	 */
	public static <T> T getLast(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}

		return list.get(list.size() - 1);
	}

	///////////////// from Guava的构造函数///////////////////
	/**
	 * 根据等号左边的类型，构造类型正确的ArrayList.
	 * 
	 * @see com.google.common.collect.Lists#newArrayList()
	 */
	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

	/**
	 * 根据等号左边的类型，构造类型正确的ArrayList, 并初始化元素.
	 * 
	 * @see com.google.common.collect.Lists#newArrayList(Object...)
	 */
	public static <T> ArrayList<T> newArrayList(T... elements) {
		return com.google.common.collect.Lists.newArrayList(elements);
	}

	/**
	 * 根据等号左边的类型，构造类型正确的ArrayList, 并初始化数组大小.
	 * 
	 * @see com.google.common.collect.Lists#newArrayListWithCapacity(int)
	 */
	public static <T> ArrayList<T> newArrayListWithCapacity(int initialArraySize) {
		return new ArrayList<T>(initialArraySize);
	}

	/**
	 * 根据等号左边的类型，构造类型正确的LinkedList.
	 * 
	 * @see com.google.common.collect.Lists#newLinkedList()
	 */
	public static <T> LinkedList<T> newLinkedList() {
		return new LinkedList<T>();
	}

	/**
	 * 根据等号左边的类型，构造类型转换的SortedArrayList.
	 * 
	 * from Jodd的新类型，插入时排序，有几个方法不支持
	 */
	public static <T extends Comparable> SortedArrayList<T> newSortedArrayList() {
		return new SortedArrayList<T>();
	}

	/**
	 * 根据等号左边的类型，构造类型转换的SortedArrayList.
	 * 
	 * from Jodd的新类型，插入时排序，有几个方法不支持
	 */
	public static <T> SortedArrayList<T> newSortedArrayList(Comparator<T> c) {
		return new SortedArrayList<T>(c);
	}
	
	/**
	 * 根据等号左边的类型，构造类型正确的CopyOnWriteArrayList.
	 * 
	 * @see com.google.common.collect.Lists#newCopyOnWriteArrayList()
	 */
	public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList() {
		return new CopyOnWriteArrayList<T>();
	}

	/**
	 * 根据等号左边的类型，构造类型转换的CopyOnWriteArrayList, 并初始化元素.
	 */
	public static <T> CopyOnWriteArrayList<T> newCopyOnWriteArrayList(T... elements) {
		return new CopyOnWriteArrayList<T>(elements);
	}
	
	/**
	 * 返回包装后同步的List，所有方法都会被synchronized原语同步，用于CopyOnWriteArrayList与
	 * ArrayDequeue均不符合
	 */
	public static <T> List<T> synchronizedList(List<T> list) {
		return Collections.synchronizedList(list);
	}

	///////////////// from JDK Collections的常用函数 ///////////////////

	/**
	 * 返回一个空的结构特殊的List，节约空间. 注意List不可写.
	 * 
	 * @see java.util.Collections#emptyList()
	 */
	public static final <T> List<T> emptyList() {
		return (List<T>) Collections.EMPTY_LIST;
	}

	/**
	 * 如果list为null，转化为一个安全的空List. 注意List不可写.
	 * 
	 * @see java.util.Collections#emptyList()
	 */
	public static <T> List<T> emptyListIfNull(final List<T> list) {
		return list == null ? (List<T>) Collections.EMPTY_LIST : list;
	}

	/**
	 * 返回一个只含一个元素但结构特殊的List，节约空间. 注意List不可写.
	 * 
	 * @see java.util.Collections#singleton(Object)
	 */
	public static <T> List<T> singletonList(T o) {
		return Collections.singletonList(o);
	}

	/**
	 * 返回包装后不可修改的List.
	 */
	public static <T> List<T> unmodifiableList(List<? extends T> list) {
		return Collections.unmodifiableList(list);
	}



	/**
	 * 排序, 采用JDK认为最优的排序算法.
	 * 
	 * @see java.util.Collections#sort(List)
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Collections.sort(list);
	}

	/**
	 * 排序, 采用JDK认为最优的排序算法.
	 * 
	 * @see java.util.Collections#sort(List, Comparator)
	 */
	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		Collections.sort(list, c);
	}

	/**
	 * 二分法快速查找对象
	 * 
	 * @see java.util.Collections#binarySearch(List, Object)
	 */
	public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
		return Collections.binarySearch(list, key);
	}

	/**
	 * 二分法快速查找对象
	 * 
	 * @see java.util.Collections#binarySearch(List, Object, Comparator)
	 */
	public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
		return Collections.binarySearch(list, key, c);
	}

	/**
	 * 随机乱序，使用默认的Random.
	 * 
	 * @see java.util.Collections#shuffle(List)
	 */
	public static void shuffle(List<?> list) {
		Collections.shuffle(list);
	}

	/**
	 * 返回一个倒转顺序访问的List，仅仅是一个倒序的View，不会实际多生成一个List
	 * 
	 * @see com.google.common.collect.Lists#reverse(List)
	 */
	public static <T> List<T> reverse(List<T> list) {
		return Lists.reverse(list);
	}

	/**
	 * 随机乱序，使用传入的Random.
	 * 
	 * @see java.util.Collections#shuffle(List, Random)
	 */
	public static void shuffle(List<?> list, Random rnd) {
		Collections.shuffle(list, rnd);
	}

	////////////////// ArrayList 与 Array的双向转换 ///////////

	/**
	 * 将List转换为数组.
	 * 
	 * 其中List的实现均以良好的初始化数组大小，不需要使用list.toArray(T[])
	 */
	public static <T> T[] toArray(List<T> list) {
		return (T[]) list.toArray();
	}

	/**
	 * 将数组转换为List.
	 * 
	 * 注意转换后的List不能写入.
	 * 
	 * @see java.util.Arrays#asList(Object...)
	 */
	public static <T> List<T> asList(T... a) {
		return Arrays.asList(a);
	}

	/**
	 * 一个独立元素＋一个数组组成新的list，而且独立元素在最前.
	 * 
	 * 注意转换后的List不能写入.
	 * 
	 * @see com.google.common.collect.Lists#asList(Object, Object[])
	 */
	public static <E> List<E> asList(E first, E[] rest) {
		return com.google.common.collect.Lists.asList(first, rest);
	}

	/**
	 * Arrays.asList()的加强版, 返回一个底层为原始类型int的List
	 * 
	 * 与保存Integer相比节约空间，同时也避免了AutoBoxing.
	 * 
	 * @see java.util.Arrays#asList(Object...)
	 * @see com.google.common.primitives.Ints#asList(int...)
	 * 
	 */
	public static List<Integer> asList(int... backingArray) {
		return Ints.asList(backingArray);
	}

	/**
	 * Arrays.asList()的加强版, 返回一个底层为原始类型long的List
	 * 
	 * 与保存Long相比节约空间，同时也避免了AutoBoxing.
	 * 
	 * @see java.util.Arrays#asList(Object...)
	 * @see com.google.common.primitives.Longs#asList(long...)
	 */
	public static List<Long> asList(long... backingArray) {
		return Longs.asList(backingArray);
	}

	/**
	 * Arrays.asList()的加强版, 返回一个底层为原始类型double的List
	 * 
	 * 与保存Double相比节约空间，同时也避免了AutoBoxing.
	 * 
	 * @see java.util.Arrays#asList(Object...)
	 * @see com.google.common.primitives.Doubles#asList(double...)
	 */
	public static List<Double> asList(double... backingArray) {
		return Doubles.asList(backingArray);
	}

	///////////////// 集合运算 ///////////////////

	/**
	 * 比较两个List中的每个元素是否相等.
	 * 
	 * from Apache Common Collections4 ListUtils
	 */
	public static boolean isEqual(final List<?> list1, final List<?> list2) {
		if (list1 == list2) {
			return true;
		}
		if (list1 == null || list2 == null || list1.size() != list2.size()) {
			return false;
		}

		final Iterator<?> it1 = list1.iterator();
		final Iterator<?> it2 = list2.iterator();
		Object obj1 = null;
		Object obj2 = null;

		while (it1.hasNext() && it2.hasNext()) {
			obj1 = it1.next();
			obj2 = it2.next();

			if (!(obj1 == null ? obj2 == null : obj1.equals(obj2))) {
				return false;
			}
		}

		return !(it1.hasNext() || it2.hasNext());
	}

	/**
	 * 返回a+b的集合.
	 * 
	 * 对比Apache Common Collection4 ListUtils, 优化了初始大小
	 */
	public static <E> List<E> union(final List<? extends E> list1, final List<? extends E> list2) {
		final ArrayList<E> result = new ArrayList<E>(list1.size() + list2.size());
		result.addAll(list1);
		result.addAll(list2);
		return result;
	}

	/**
	 * 返回a与b的交集的新List（重复元素将去重）
	 * 
	 * from Apache Common Collection4 ListUtils
	 */
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		final List<T> result = new ArrayList<T>();

		List<? extends T> smaller = list1;
		List<? extends T> larger = list2;
		if (list1.size() > list2.size()) {
			smaller = list2;
			larger = list1;
		}

		final HashSet<T> hashSet = new HashSet<T>(smaller);

		for (final T e : larger) {
			if (hashSet.contains(e)) {
				result.add(e);
				hashSet.remove(e);
			}
		}
		return result;
	}
}
