package co.com.linktic.api.dto.response;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;
    private Map<String, Object> metadata;

    public static <T> ApiResponse<T> success(T data, String message, Map<String, Object> metadata) {
        return ApiResponse.<T>builder()
                .success(true)
                .code("SUCCESS")
                .message(message)
                .data(data)
                .metadata(metadata)
                .build();
    }

    public static ApiResponse<Object> error(Object data, String code, String message, Map<String, Object> metadata) {
        return ApiResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .data(null)
                .metadata(metadata)
                .build();
    }


}
