package rental.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.template.UriUtils;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import rental.presentation.dto.response.common.ErrorResponse;
import rental.presentation.exception.InternalServerException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Configuration
public class FeignClientConfig {
}
