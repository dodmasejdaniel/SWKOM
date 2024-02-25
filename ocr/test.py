import unittest
from unittest.mock import patch, MagicMock, ANY

from ocr import (
    on_document_received,
)


class Test(unittest.TestCase):

    @patch('ocr.Minio')
    @patch('psycopg2.connect')
    @patch('ocr.convert_from_bytes', return_value=[MagicMock()])
    @patch('pytesseract.image_to_string', return_value='mocked text')
    def test_on_document_received(self, mock_image_to_string, convert_from_bytes_mock, mock_connect, mock_minio):
        # Mocking Minio client
        mock_minio_instance = MagicMock()
        mock_minio.return_value = mock_minio_instance
        mock_minio_instance.get_object.return_value.data = b"Mock PDF data"

        # Mocking PostgreSQL connection
        mock_conn = MagicMock()

        # Call the function to be tested
        on_document_received(b"bucket_name/document_path", mock_minio_instance, mock_conn)

        # Assertions
        mock_minio_instance.get_object.assert_called_once_with("bucket_name", "document_path")
        self.assertEqual(mock_conn.cursor().execute.call_count, 2)
        expected_update_sql = "UPDATE documents_document SET content = %s WHERE id = %s;"
        mock_conn.cursor().execute.assert_called_with(expected_update_sql, (
            "mocked text\n", ANY))
        mock_conn.commit.assert_called_once()
        mock_conn.cursor().close.assert_called_once()


if __name__ == "__main__":
    unittest.main()
