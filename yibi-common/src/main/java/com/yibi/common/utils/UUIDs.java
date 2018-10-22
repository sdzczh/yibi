package com.yibi.common.utils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table.Cell;

import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class UUIDs {
  private UUIDs() {
  }

  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
  
  
  public static void main(String[] args) {
	HashBasedTable< Integer, Integer, String> h = HashBasedTable.create();
	h.put(14, 0, "KN币");
	h.put(14, 1, "BTC币");
	h.put(14, 2, "USDT币");
	h.put(14, 6, "HLC币");
	h.put(14, 7, "HYC币");
	h.put(15, 7, "17HYC币");
	
	Set<Integer> s= h.columnKeySet();
	System.out.println(s.toString());
	Set<Cell<Integer, Integer, String>> c = h.cellSet();
	System.out.println(c.toString());
	
	Set<Integer> r = h.rowKeySet();
	System.out.println(r.toString());
	
	Map<Integer, String> hc = h.row(14);
	System.out.println(hc);
	
	Map<Integer, String> cc = h.column(7);
	System.out.println(cc);
}
}
