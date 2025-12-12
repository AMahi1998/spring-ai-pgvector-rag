package dev.mahi.rag_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class IngestionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectoreStore;
    @Value("classpath:/docs/article_thebeatoct2024.pdf")
    private Resource marketPDF;

    public IngestionService(VectorStore vectoreStore) {
        this.vectoreStore = vectoreStore;
    }

    @Override
    public void run(String... args) throws Exception {
        var pdfReader = new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectoreStore.accept(textSplitter.apply(pdfReader.get()));
        log.info("VectorStore loaded with documents from PDF!");

    }
}
