/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils.components;

/**
 *
 * @author david
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoomPanel extends JPanel {

    private final List<Seat> seats = new ArrayList<>();

    public RoomPanel(int rows, int cols) {
        setLayout(new GridLayout(rows, cols, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {

                String id = (char) ('A' + r - 1) + String.valueOf(c);
                Seat seat = new Seat(id);

                seats.add(seat);
                add(seat);
            }
        }
    }

    public List<Seat> getSelectedSeats() {
        List<Seat> selected = new ArrayList<>();
        for (Seat s : seats) {
            if (s.isSelected()) selected.add(s);
        }
        return selected;
    }

    // Puedes marcar asientos ocupados:
    public void setOccupiedSeats(List<String> occupiedIds) {
        for (Seat s : seats) {
            if (occupiedIds.contains(s.getSeatId())) {
                s.setOccupied();
            }
        }
    }
}
