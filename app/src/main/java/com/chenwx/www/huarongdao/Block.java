package com.chenwx.www.huarongdao;

public class Block {
    public int lx, ly, rx, ry;
    public int width, height;
    public int size;
    public String name;

    public Block(int lx, int ly, int rx, int ry, int size, String name) {
        this.lx = lx;
        this.ly = ly;
        this.rx = rx;
        this.ry = ry;
        this.size = size;
        this.width = rx - lx;
        this.height = ry - ly;
        this.name = name;
    }

    public Block(int lx, int ly, int size, String name) {
        this.lx = lx;
        this.ly = ly;
        this.size = size;
        switch (size) {
            case 1:
                rx = lx + 1;
                ry = ly + 1;
                break;
            case 2:
                rx = lx + 1;
                ry = ly + 2;
                break;
            case 3:
                rx = lx + 2;
                ry = ly + 1;
                break;
            case 4:
                rx = lx + 2;
                ry = ly + 2;
                break;
        }
        this.width = rx - lx;
        this.height = ry - ly;
        this.name = name;
    }

    public Block(String value[]) {
        lx = Integer.parseInt(value[0]);
        ly = Integer.parseInt(value[1]);
        size = Integer.parseInt(value[2]);
        switch (size) {
            case 1:
                rx = lx + 1;
                ry = ly + 1;
                break;
            case 2:
                rx = lx + 1;
                ry = ly + 2;
                break;
            case 3:
                rx = lx + 2;
                ry = ly + 1;
                break;
            case 4:
                rx = lx + 2;
                ry = ly + 2;
                break;
        }
        width = rx - lx;
        height = ry - ly;
        name = value[3];
    }

    public void getLocation(Block block) {
        this.lx = block.lx;
        this.ly = block.ly;
        this.rx = block.rx;
        this.ry = block.ry;
    }

    public void updateSize() {
        width = rx - lx;
        height = ry - ly;
    }

    public void updateLocXByr() {
        lx = rx - width;
    }

    public void updateLocYByr() {
        ly = ry - height;
    }

    @Override
    public String toString() {
        return String.format("%d %d %d %s", lx, ly, size, name);
    }
}
