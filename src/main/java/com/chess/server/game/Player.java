package com.chess.server.game;

import java.util.Objects;

public class Player {

    private String login;
    private int id;

    public Player(int id, String login){
        this.login=login;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLogin(){
        return login;
    }

    @Override
    public String toString() {
        return "Player{" +
                "login='" + login + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId() == player.getId() &&
                Objects.equals(getLogin(), player.getLogin());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLogin(), getId());
    }
}
