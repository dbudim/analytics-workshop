package com.dbudim.analytics.api;

import com.dbudim.analytics.Owner;
import com.dbudim.analytics.api.models.Board;
import com.dbudim.analytics.api.models.MeModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.util.Strings;
import retrofit2.Response;

import static com.dbudim.analytics.api.Executor.execute;
import static java.lang.System.currentTimeMillis;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by dbudim on 10.07.2021
 */

@Owner(name = "John")
public class BoardServiceTest {

    private TrelloClient client = new TrelloClient();

    @BeforeClass
    public void clearBoards() {
        MeModel me = execute(client.memberService.getMe("open")).body();
        me.idBoards.stream().forEach(b -> execute(client.boardsService.deleteBoard(b)));
    }

    @Test
    public void createBoard() {
        var boardName = "board " + currentTimeMillis();
        Board board = execute(client.boardsService.createBoard(new Board(boardName))).body();
        assertTrue(Strings.isNotNullAndNotEmpty(board.id), "bard id is broken");
    }

    @Test
    public void updateBoard() {
        Board board = execute(client.boardsService.createBoard(new Board("for update " + currentTimeMillis()))).body();

        var newName = "new name " + currentTimeMillis();
        Board updatedBoard = execute(client.boardsService.updateBoard(board.id, new Board(newName))).body();
        assertEquals(updatedBoard.name, newName);
    }

    @Test
    public void deleteBoard() {
        Board board = execute(client.boardsService.createBoard(new Board("for delete " + currentTimeMillis()))).body();
        execute(client.boardsService.deleteBoard(board.id));

        client.interceptors.asserterInterceptor.disableOnce();
        Response<Board> response = execute(client.boardsService.getBoard(board.id));
        assertEquals(response.code(), 404, "response code doesn't match");
    }

}
