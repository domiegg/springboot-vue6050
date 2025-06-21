package com.daowen.util;

import java.util.*;

public class MyUserCF {



    public static String Similar(Map<String,Map<String,Double>> mapUsers,String key){
        //获取用户信息
        Map<String, Double> mapSim = new HashMap<String, Double>();//存放相似度集合
        Map<String, Double> currentUserEnt=mapUsers.get(key);//获取当前用户
        //遍历每个用户
        for (Map.Entry<String, Map<String, Double>> userEnt : mapUsers.entrySet()){
            String curKey = userEnt.getKey();
            if(!key.equals(curKey)){//遍历到的用户非当前用户
                double sim = getUserSimilar(currentUserEnt, userEnt.getValue());
                System.out.println(key+"与"+curKey+"的相似度为："+String.valueOf(sim));
                mapSim.put(curKey,sim);
            }
        }
        List<Map.Entry<String, Double>> wordMap = new ArrayList<>(mapSim.entrySet());
        Collections.sort(wordMap, new Comparator<Map.Entry<String, Double>>() {// 根据value排序
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                double result = o2.getValue() - o1.getValue();
                if (result > 0)
                    return 1;
                else if (result == 0)
                    return 0;
                else
                    return -1;
            }
        });
        for(Map.Entry<String, Double> set:wordMap){
            System.out.println("map:"+set.getKey() +"   "+set.getValue());
        }
        if(wordMap.size()>0)
           return wordMap.get(0).getKey();
        return null;


    }



    public static void test(int id){
        //获取用户信息
        Map<String, Map<String, Double>> mapUsers = new HashMap<String, Map<String, Double>>();

        //用户行为矩阵
        Map<String, Double> u1 = new HashMap<String, Double>();
        u1.put("1", 1.0);
        u1.put("2", 3.0);
        u1.put("3", 4.0);
        u1.put("4",0.0);

        mapUsers.put("1",u1);

        Map<String, Double> u2 = new HashMap<String, Double>();
        u2.put("1", 3.0);
        u2.put("2", 3.0);
        u2.put("3", 4.0);
        u2.put("4",0.0);
        mapUsers.put("2",u2);
        Map<String, Double> u3 = new HashMap<String, Double>();
        u3.put("1", 0.0);
        u3.put("2", 3.0);
        u3.put("3", 8.0);
        u3.put("4",4.0);
        mapUsers.put("3",u3);


        Map<String, Double> mapSim = new HashMap<String, Double>();//存放相似度集合
        Map<String, Double> currentUserEnt=mapUsers.get(String.valueOf(id));//获取当前用户
        //遍历每个用户
        for (Map.Entry<String, Map<String, Double>> userEnt : mapUsers.entrySet()){
            String perId = userEnt.getKey();
            if(!new Integer(id).toString().equals(perId)){//遍历到的用户非当前用户
                double sim = getUserSimilar(currentUserEnt, userEnt.getValue());
                System.out.println(id+"与"+perId+"的相似度为："+String.valueOf(sim));
                mapSim.put(perId,sim);
            }
        }
        List<Map.Entry<String, Double>> wordMap = new ArrayList<>(mapSim.entrySet());
        Collections.sort(wordMap, new Comparator<Map.Entry<String, Double>>() {// 根据value排序
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                double result = o2.getValue() - o1.getValue();
                if (result > 0)
                    return 1;
                else if (result == 0)
                    return 0;
                else
                    return -1;
            }
        });
        for(Map.Entry<String, Double> set:wordMap){
            System.out.println("map:"+set.getKey() +"   "+set.getValue());
        }
        String maxSimilar = wordMap.get(0).getKey();
        System.out.println("最大相识"+maxSimilar);

    }



    private static double getUserSimilar(Map<String, Double> pm1, Map<String, Double> pm2) {
        int n = pm1.size();// 数量n
        Double sxy = 0.0;// Σxy=x1*y1+x2*y2+....xn*yn
        Double sx = 0.0;// Σx=x1+x2+....xn
        Double sy = 0.0;// Σy=y1+y2+...yn
        Double sx2 = 0.0;// Σx2=(x1)2+(x2)2+....(xn)2
        Double sy2 = 0.0;// Σy2=(y1)2+(y2)2+....(yn)2
        for (Map.Entry<String, Double> pme : pm1.entrySet()) {
            String key = pme.getKey();
            System.out.println("key:"+key);
            Double x = pme.getValue();
            Double y = pm2.get(key);
            System.out.println("x:"+String.valueOf(x)+",y:"+String.valueOf(y));
            if (x != null && y != null) {
                sxy += x * y;//x*y求和
                sx += x;//x求和
                sy += y;//y求和
                sx2 += Math.pow(x, 2.0);//x的平方求和
                sy2 += Math.pow(y, 2.0);//y的平方求和
                System.out.println(":sxy:"+sxy+",sx:"+sx+",sy"+",sx2:"+sx2+",sy2:"+sy2);
            }
        }
        double sd = sxy - (sx * sy) / n;
        double sm = Math.sqrt((sx2 - (Math.pow(sx, 2) / n)) * (sy2 - (Math.pow(sy, 2) / n)));
        System.out.println("sd:"+sd+",sm:"+sm);
        return Math.abs(sm == 0 ? 0 : sd / sm);
    }
}
