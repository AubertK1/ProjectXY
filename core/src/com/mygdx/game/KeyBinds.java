package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.mygdx.game.Main.controlScreen;

public class KeyBinds {

    /**
     * Default keyset. This will NEVER change. It's the
     * MAKE SURE THE KEYS ON HERE ALIGN WITH THE KEYS ON THE OTHER KEYSETS
     */
    final static int[] defaultKeys = new int[]{
            KeyBinds.Keys.JUMP,
            KeyBinds.Keys.LEFT,
            KeyBinds.Keys.DOWN,
            KeyBinds.Keys.RIGHT,
            KeyBinds.Keys.INTERACT,
            KeyBinds.Keys.LIGHTATTACK,
            KeyBinds.Keys.HEAVYATTACK,
            KeyBinds.Keys.TEMP
    };
    /**
     * Player 1 will always be using this keyset
     * MAKE SURE THE KEYS ON HERE ALIGN WITH THE KEYS ON THE OTHER KEYSETS
     */
    final static int[] keyset0 = new int[]{
            Input.Keys.W,
            Input.Keys.A,
            Input.Keys.S,
            Input.Keys.D,
            Input.Keys.E,
            Input.Keys.F,
            Input.Keys.G,
            Input.Keys.Y
    };

    public static HashMap<Integer, Integer> keySetMap = new HashMap<Integer, Integer>() {
        {
            put(1, 1);
        }
    };


    /**
     * Player 2 will always be using this keyset
     * MAKE SURE THE KEYS ON HERE ALIGN WITH THE KEYS ON THE OTHER KEYSETS
     */
    final static int[] keyset1 = new int[]{
            Input.Keys.UP,
            Input.Keys.LEFT,
            Input.Keys.DOWN,
            Input.Keys.RIGHT,
            Input.Keys.CONTROL_RIGHT,
            Input.Keys.COMMA,
            Input.Keys.PERIOD,
            Input.Keys.BACKSLASH
    };

    private final static int[][] keysets = new int[][]{
            keyset0, keyset1
    };
    public static int convertKey(int KEY){
        int newKey = findKeyIndex(KEY);

        if(newKey == -2){
            System.out.println("Key does not exist");
            return KEY;
        }
        else return defaultKeys[newKey];
    }

    public static boolean changeKeyBind(int oldKey, int newKey){
        if(findKeyIndex(newKey)>=0){
            return false;
        }
        keysets[findKeySetIndex(oldKey)][findKeyIndex(oldKey)] = newKey;
        System.out.print("key changed");
        return true;
    }

    public static int findKeyFromDefaultKey(int defaultKey, int keyset){
        int defaultKeyIndex = -1;
        for (int i = 0; i < defaultKeys.length; i++) {
            if(defaultKeys[i] == defaultKey) defaultKeyIndex = i;
        }
        return keysets[keyset][defaultKeyIndex];
    }

    private static int findKeyIndex(int KEY){
        //loops through each keyset and finds the key, then returns the key's index in the keyset
        for (int keyset = 0; keyset < keysets.length; keyset++) {
            for (int key = 0; key < keysets[keyset].length; key++) {
                if(KEY == keysets[keyset][key]) return key;
            }
        }
        return -2;
    }

    public static void resetKeyBinds(){
                keysets[0][KeyBinds.Keys.JUMP] = Input.Keys.W;

    }

    public static int findKeySetIndex(int KEY){
        //loops through each keyset and finds the key, then returns the keyset it was found in's index
        for (int keyset = 0; keyset < keysets.length; keyset++) {
            for (int key = 0; key < keysets[keyset].length; key++) {
                if(KEY == keysets[keyset][key]) return keyset;
            }
        }
        return -1;
    }

    public static boolean isKeyPressed(int key, int keySet){
        int KEY = findKeyFromDefaultKey(key, keySet);

        return Gdx.input.isKeyPressed(KEY);
    }

    public static class Keys{
        public static final int LEFT = Input.Keys.A;
        public static final int RIGHT = Input.Keys.D;
        public static final int DOWN = Input.Keys.S;
        public static final int JUMP = Input.Keys.W;
        public static final int INTERACT = Input.Keys.E;
        public static final int LIGHTATTACK = Input.Keys.F;
        public static final int HEAVYATTACK = Input.Keys.G;
        public static final int TEMP = Input.Keys.Y;
    }
}
