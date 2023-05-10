package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.OI.ControlScreen;

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
    };
    /**
     * Player 1 will always be using this keyset
     * MAKE SURE THE KEYS ON HERE ALIGN WITH THE KEYS ON THE OTHER KEYSETS
     */
     static int[] keyset0 = new int[]{
            Input.Keys.W,
            Input.Keys.A,
            Input.Keys.S,
            Input.Keys.D,
            Input.Keys.E,
            Input.Keys.F,
            Input.Keys.G,
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
     static int[] keyset1 = new int[]{
            Input.Keys.UP,
            Input.Keys.LEFT,
            Input.Keys.DOWN,
            Input.Keys.RIGHT,
            Input.Keys.CONTROL_RIGHT,
            Input.Keys.COMMA,
            Input.Keys.PERIOD,
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
        keysets[0][0] = Input.Keys.W;//jump
        ControlScreen.upP1Text = Input.Keys.toString(Input.Keys.W);
        keysets[0][1] = Input.Keys.S;
        ControlScreen.downP1Text = Input.Keys.toString(Input.Keys.S);
        keysets[0][2] = Input.Keys.A;
        ControlScreen.leftP1Text = Input.Keys.toString(Input.Keys.A);
        keysets[0][3] = Input.Keys.D;
        ControlScreen.rightP1Text = Input.Keys.toString(Input.Keys.D);
        keysets[0][4] = Input.Keys.E;
        ControlScreen.interactP1text = Input.Keys.toString(Input.Keys.E);
        keysets[0][5] = Input.Keys.F;
        ControlScreen.lightP1Text = Input.Keys.toString(Input.Keys.F);
        keysets[0][6] = Input.Keys.G;
        ControlScreen.heavyP1Text = Input.Keys.toString(Input.Keys.G);

        keysets[1][0] = Input.Keys.UP;
        ControlScreen.upP2Text = Input.Keys.toString(Input.Keys.UP);
        keysets[1][1] = Input.Keys.LEFT;
        ControlScreen.leftP2Text = Input.Keys.toString(Input.Keys.LEFT);
        keysets[1][2] = Input.Keys.DOWN;
        ControlScreen.downP2Text = Input.Keys.toString(Input.Keys.DOWN);
        keysets[1][3] = Input.Keys.RIGHT;
        ControlScreen.rightP2Text = Input.Keys.toString(Input.Keys.RIGHT);
        keysets[1][4] = Input.Keys.CONTROL_RIGHT;
        ControlScreen.interactP2text = Input.Keys.toString(Input.Keys.CONTROL_RIGHT);
        keysets[1][5] = Input.Keys.SLASH;
        ControlScreen.lightP2Text = Input.Keys.toString(Input.Keys.SLASH);
        keysets[1][6] = Input.Keys.PERIOD;
        ControlScreen.heavyP2Text = Input.Keys.toString(Input.Keys.PERIOD);
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
