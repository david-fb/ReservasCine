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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Seat extends JPanel {

    public enum SeatState {
        FREE, SELECTED, OCCUPIED
    }

    private SeatState state = SeatState.FREE;
    private final String seatId;

    private final JLabel iconLabel;

    private final ImageIcon iconFree;
    private final ImageIcon iconSelected;
    private final ImageIcon iconOccupied;

    public Seat(String seatId) {
        this.seatId = seatId;

        iconFree = new ImageIcon("src/assets/seat/seat-free-icon.png");
        iconSelected = new ImageIcon("src/assets/seat/seat-selected-icon.png");
        iconOccupied = new ImageIcon("src/assets/seat/seat-occupied-icon.png");

        setLayout(new BorderLayout());
        setOpaque(false);

        iconLabel = new JLabel(iconFree);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(iconLabel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(48, 48));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    private void toggleSelection() {
        if (state == SeatState.OCCUPIED) {
            return;
        }

        if (state == SeatState.FREE) {
            state = SeatState.SELECTED;
            iconLabel.setIcon(iconSelected);
        } else if (state == SeatState.SELECTED) {
            state = SeatState.FREE;
            iconLabel.setIcon(iconFree);
        }
    }

    public void setOccupied() {
        state = SeatState.OCCUPIED;
        iconLabel.setIcon(iconOccupied);
    }

    public boolean isSelected() {
        return state == SeatState.SELECTED;
    }

    public String getSeatId() {
        return seatId;
    }

    public SeatState getState() {
        return state;
    }
}
