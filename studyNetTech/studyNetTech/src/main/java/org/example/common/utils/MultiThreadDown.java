package org.example.common.utils;

public class MultiThreadDown {
    public static void main(String[] args) throws Exception {
        final DownUtil downUtil = new DownUtil("https://ss2.bdstatic.com/" +
                "70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1148403334," +
                "3995170544&fm=15&gp=0.jpg", "./image/img.png", 4);
        downUtil.download();
        new Thread(() -> {
            while (downUtil.getCompleteRate() < 1){
                // 每隔 0.1 秒查询一次任务的完成进度
                // GUI 程序中可根据该进度来绘制进度条
                System.out.println("已完成: " + downUtil.getCompleteRate());
                try {
                    Thread.sleep(200);
                }catch (Exception e){

                }
            }
        }).start();
    }
}
