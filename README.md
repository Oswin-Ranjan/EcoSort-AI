# 🌱 EcoSort AI

EcoSort AI is an AI-powered sustainability assistant that helps users make environmentally responsible waste disposal decisions. The application combines an Android frontend, a Spring Boot backend, a Retrieval-Augmented Generation (RAG) pipeline, a PDF-based sustainability knowledge base, Google ML Kit image analysis, and the Mistral 7B Large Language Model to provide intelligent waste management recommendations.

---

## 🚀 Features

### ♻️ AI-Powered Waste Guidance

* Ask sustainability and waste disposal questions in natural language.
* Receive context-aware recommendations generated using Mistral 7B.
* Confidence-based response filtering for improved reliability.

### 📚 Retrieval-Augmented Generation (RAG)

* Uses a curated sustainability knowledge base stored as PDF documents.
* Retrieves the most relevant document using a confidence-based retrieval engine.
* Generates grounded answers using retrieved sustainability knowledge.

### 🔍 Smart Document Retrieval

* Keyword-based relevance scoring.
* Confidence score generation.
* Automatic source identification.
* Low-confidence query handling.

### 📷 AI Waste Scanner

* Capture images using the device camera.
* Analyze images using Google ML Kit Image Labeling.
* Detect common waste categories including:

  * Batteries
  * Plastic Waste
  * Electronic Waste
  * Organic Waste

* Automatically generate disposal recommendations based on detected items.

### 📝 Query History

* Stores previous user queries and responses.
* View historical sustainability interactions.
* Clear history functionality.

### 🎨 Modern Android Experience

* Material Design UI.
* Custom EcoSort AI branding.
* Animated splash screen.
* Confidence score visualization.
* Error and loading state handling.

### 🌍 Sustainability Knowledge Base

* Battery Disposal Guide
* Plastic Recycling Guide
* E-Waste Management Guide
* Composting Guide
* Waste Segregation Guide

---

## 🏗️ System Architecture

Android App -> Retrofit API -> Spring Boot Backend -> Query Service -> RAG Retrieval Engine -> PDF Knowledge Base -> Mistral 7B LLM -> AI Generated Response

---

## ⚙️ Technology Stack

### Frontend

* Java
* Android Studio
* Material Design Components
* Retrofit
* OkHttp
* SharedPreferences

### Backend

* Spring Boot
* Maven
* REST APIs
* Apache PDFBox

### AI & Machine Learning

* Hugging Face Inference API
* Mistral 7B Instruct
* Retrieval-Augmented Generation (RAG)
* Google ML Kit Image Labeling
* Confidence-Based Document Ranking

### Knowledge Storage

* PDF Knowledge Repository
* SharedPreferences

---

## 📂 Project Workflow

### Text Query Flow

1. User submits a sustainability question.
2. Query is sent from Android to Spring Boot through Retrofit.
3. Retrieval Engine identifies the most relevant PDF document.
4. PDFBox extracts contextual information.
5. Context and query are sent to Mistral 7B.
6. AI generates a context-aware recommendation.
7. Response, source document, and confidence score are returned to the Android application.

### Waste Scanner Flow

1. User captures an image.
2. Google ML Kit analyzes the image.
3. Waste category or item is identified.
4. EcoSort AI generates an appropriate disposal query.
5. Query is sent through the RAG pipeline.
6. Mistral generates sustainability guidance.

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

## 🎯 Key Highlights

* Android + Spring Boot Full Stack Architecture
* Retrieval-Augmented Generation (RAG)
* Mistral 7B Integration
* Google ML Kit Waste Scanner
* PDF-Based Knowledge Retrieval
* Confidence-Based Answer Validation
* Material Design User Experience

---

## 🔮 Future Enhancements

* Custom waste classification model
* Location-based recycling center recommendations
* Voice-enabled sustainability assistant
* Cloud-based query history
* Multi-language support
* Semantic search using vector embeddings
* Real-time recycling center discovery
