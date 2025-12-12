# Spring AI RAG

A demonstration project showcasing **Retrieval-Augmented Generation (RAG)** using **Spring AI** and **OpenAI GPT models**.  
This application enables intelligent document querying by combining the power of **Large Language Models (LLMs)** with **local document context** stored in a vector database.



## 🔍 Overview

This project demonstrates how to:

- Ingest PDF documents into a vector database
- Generate embeddings using OpenAI via Spring AI
- Perform semantic similarity search using PostgreSQL pgvector
- Augment LLM responses with relevant document context
- Build the foundation for document-aware chat APIs



## 🧱 Project Requirements

- Java **23**
- Maven
- Docker Desktop
- OpenAI API Key
- Spring Initializer (Spring Boot project setup)



## 📦 Dependencies

The project uses the following Spring Boot starters and Spring AI dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-pdf-document-reader</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-pgvector-store-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

# Getting Started

## Configure your environment variables:
```
OPENAI_API_KEY=your_api_key_here
Update application.properties:
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.model=gpt-4
spring.ai.vectorstore.pgvector.initialize-schema=true
```
### Place your PDF documents in the src/main/resources/docs directory
### Running the Application
### Start Docker Desktop

### Launch the application:
```
./mvnw spring-boot:run
```
The application will:
Start a PostgreSQL database with PGVector extension
Initialize the vector store schema
Ingest documents from the configured location
Start a web server on port 8080

# Key Components
## IngestionService
The IngestionService handles document processing and vector store population:
```
@Component
public class IngestionService implements CommandLineRunner {
    private final VectorStore vectorStore;
    
    @Value("classpath:/docs/your-document.pdf")
    private Resource marketPDF;
    
    @Override
    public void run(String... args) {
        var pdfReader = new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
    }
}
```
<img width="1920" height="1128" alt="Screenshot 2025-12-11 201112" src="https://github.com/user-attachments/assets/4803e9ea-925a-4cba-8891-8c77a24bae22" />
<img width="1900" height="1081" alt="Screenshot 2025-12-11 201305" src="https://github.com/user-attachments/assets/e89c6ca6-7820-446c-8c56-032ba95dac83" />

