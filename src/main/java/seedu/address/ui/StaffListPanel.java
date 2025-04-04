package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Staff;

/**
 * Panel containing the list of staff.
 */
public class StaffListPanel extends UiPart<Region> {
    private static final String FXML = "StaffListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StaffListPanel.class);

    @FXML
    private ListView<Staff> staffListView;

    /**
     * Creates a {@code StaffListPanel} with the given {@code ObservableList}.
     */
    public StaffListPanel(ObservableList<Staff> staffList) {
        super(FXML);
        staffListView.setItems(staffList);
        staffListView.setCellFactory(listView -> new StaffListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Staff} using a {@code StaffCard}.
     */
    class StaffListViewCell extends ListCell<Staff> {
        @Override
        protected void updateItem(Staff staff, boolean empty) {
            super.updateItem(staff, empty);

            if (empty || staff == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StaffCard(staff, getIndex() + 1).getRoot());
            }
        }
    }

}
