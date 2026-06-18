# 🌱 EcoSort AI

EcoSort AI is a Retrieval-Augmented Generation (RAG) based sustainability assistant that helps users make environmentally responsible waste disposal decisions. The application combines an Android frontend, a Spring Boot backend, a PDF-based knowledge repository, and the Mistral 7B Large Language Model to generate intelligent waste management recommendations.

---

## 🚀 Features

### ♻️ AI-Powered Waste Guidance

* Ask sustainability and waste disposal questions in natural language.
* Receive AI-generated recommendations powered by Mistral 7B.

### 📚 Retrieval-Augmented Generation (RAG)

* Uses a curated sustainability knowledge base stored as PDF documents.
* Retrieves the most relevant document using a confidence-based retrieval engine.
* Generates context-aware answers using retrieved knowledge.

### 🔍 Smart Document Retrieval

* Keyword scoring and ranking system.
* Confidence score generation for retrieved documents.
* Automatic source identification.

### 📷 Waste Scanner

* Capture images using the device camera.
* Preview scanned waste items.
* Foundation for future image-based waste classification.

### 📝 Query History

* Stores previous user queries and responses.
* View and manage historical interactions.
* Clear history functionality.

### 🌍 Sustainability Knowledge Base

* Battery Disposal Guide
* Plastic Recycling Guide
* E-Waste Management Guide
* Composting Guide
* Waste Segregation Guide

---

## 🏗️ System Architecture

Android App
↓
Retrofit API
↓
Spring Boot Backend
↓
Query Service
↓
Smart Retrieval Engine
↓
PDF Knowledge Base
↓
Mistral 7B LLM
↓
AI Generated Response

---

## ⚙️ Technology Stack

### Frontend

* Java
* Android Studio
* Material Design Components
* Retrofit
* OkHttp

### Backend

* Spring Boot
* Maven
* REST APIs
* Apache PDFBox

### AI & RAG

* Hugging Face Inference API
* Mistral 7B Instruct
* Retrieval-Augmented Generation (RAG)
* Confidence-Based Document Ranking

### Data Storage

* SharedPreferences
* PDF Knowledge Repository

---

## 📂 Project Workflow

1. User submits a sustainability query.
2. Query is sent from Android to Spring Boot through Retrofit.
3. Retrieval Engine identifies the most relevant PDF document.
4. PDFBox extracts contextual information.
5. Context and query are sent to Mistral 7B.
6. AI generates a context-aware recommendation.
7. Response, source document, and confidence score are returned to the Android application.

---

## 🎯 Example Query

Question:
How should I dispose of a used lithium battery?

Response:
Used lithium batteries should never be disposed of in household trash. They should be taken to authorized recycling centers where hazardous materials can be safely processed. Proper recycling helps prevent environmental contamination and enables recovery of valuable materials.

Source:
Battery_Disposal_Guide.pdf

Confidence:
70%

---

## 🔮 Future Enhancements

* Real-time waste image classification
* Location-based recycling center recommendations
* Voice-enabled sustainability assistant
* Cloud-based query history
* Multi-language support
* Advanced semantic search using vector embeddings

---

## 👨‍💻 Author

**Oswin Ranjan**

B.Tech CSE (AI & ML)

GL Bajaj Institute of Technology & Management

Passionate about Android Development, Artificial Intelligence, Machine Learning, and Sustainable Technology.

---

## 📜 License

This project is developed for educational and internship purposes.
