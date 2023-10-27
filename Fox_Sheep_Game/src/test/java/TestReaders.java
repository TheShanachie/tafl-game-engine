import com.github.theshanachie.board_utils.AdjacencyListReader;
import com.github.theshanachie.board_utils.BoardReader;
import com.github.theshanachie.board_utils.BoardVal;
import com.github.theshanachie.board_utils.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestReaders {

    /****** TEST ADJACENCY LIST READER ******/
    @Test
    void invalid_file_AL() // test invalid file format.
    {
        try {
            Throwable throwable;
            throwable =  assertThrows(Throwable.class, () -> {
                new AdjacencyListReader("src/test/resources/invalid_adjacencylist_00.txt");
            });

            throwable =  assertThrows(Throwable.class, () -> {
                new AdjacencyListReader("src/test/resources/invalid_adjacencylist_01.txt");
            });

            throwable =  assertThrows(Throwable.class, () -> {
                new AdjacencyListReader("src/test/resources/invalid_adjacencylist_02.txt");
            });
        } catch (Exception ex) {
            fail( ex );
        }
    }

    @Test
    void empty_file_AL() // test valid file format.
    {
        try {
            String path = "src/test/resources/empty_file.txt";
            AdjacencyListReader list = new AdjacencyListReader(path);
            assertNotNull(list);
            assertNotNull(list.getAdjacencyList());
            assertEquals( list.getAdjacencyList().size(), 0);
        } catch (Exception ex) {
            fail( ex );
        }
    }

    @Test
    void valid_file_AL() // test valid file format.
    {
        try {
            AdjacencyListReader reader = new AdjacencyListReader("src/test/resources/valid_adjacencylist.txt");
            Map<Position, ArrayList<Position>> map = reader.getAdjacencyList();

            // assert that all of these keys exist in the map.
            assertTrue(map.containsKey( new Position(0,0)));
            assertTrue(map.containsKey( new Position(0,1)));
            assertTrue(map.containsKey( new Position(1,0)));
            assertTrue(map.containsKey( new Position(1,1)));
            assertEquals(map.keySet().size(), 4);

            // assert that the mapped Positions follow the expectations.
            ArrayList<Position> list;
            // For (0,0)
            list = map.get( new Position(0,0));
            assertTrue( list.contains( new Position( 1, 0)) );
            assertTrue( list.contains( new Position( 0, 1)) );
            assertEquals( list.size(), 2);
            // For (0,1)
            list = map.get( new Position(0,1));
            assertTrue( list.contains( new Position( 0, 0)) );
            assertTrue( list.contains( new Position( 1, 1)) );
            assertEquals( list.size(), 2);
            // For (1,0)
            list = map.get( new Position(1,0));
            assertTrue( list.contains( new Position( 0, 0)) );
            assertTrue( list.contains( new Position( 1, 1)) );
            assertEquals( list.size(), 2);
            // For (1,1)
            list = map.get( new Position(1,1));
            assertTrue( list.contains( new Position( 1, 0)) );
            assertTrue( list.contains( new Position( 0, 1)) );
            assertEquals( list.size(), 2);

        } catch (Exception ex) {
            fail( ex );
        }
    }



    /****** TEST BOARD READER ******/
    @Test
    void invalid_file_B() // test an invalid file.
    {
        Throwable throwable;
        throwable =  assertThrows(Throwable.class, () -> {
            new BoardReader("src/test/resources/invalid_board_00.txt");
        });
    }

    @Test
    void empty_file_B() // test valid file format.
    {
        try {
            String path = "src/test/resources/empty_file.txt";
            BoardReader board = new BoardReader(path);
            assertNotNull( board );
            assertNull( board.getBoard() );
        } catch (Exception ex) {
            fail( ex );
        }
    }

    @Test
    void valid_file_B() // test valid file format.
    {
        try {
            String path = "src/test/resources/valid_board.txt";
            BoardReader reader = new BoardReader(path);
            BoardVal[][] board = reader.getBoard();
            //System.out.println("Input from file: \"" + path + "\".");
            for (int i = 0; i < board.length; i++) {
                int len = board[0].length;
                assertEquals(len, board[i].length);
                for (int j = 0; j < board[i].length; j++)
                {
                    assertNotNull( board[i][j] );
                    //System.out.print( board[i][j].key );
                }
                // System.out.println();
            }
        } catch (Exception ex) {
            fail ( ex );
        }
    }
}
